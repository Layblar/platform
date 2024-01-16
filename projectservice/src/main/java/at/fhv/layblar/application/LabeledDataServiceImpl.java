package at.fhv.layblar.application;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import at.fhv.layblar.application.dto.LabelEventDTO;
import at.fhv.layblar.application.dto.LabeledDataDTO;
import at.fhv.layblar.commands.AddLabeledDataCommand;
import at.fhv.layblar.commands.RemoveLabeledDataCommand;
import at.fhv.layblar.commands.UpdateLabeledDataCommand;
import at.fhv.layblar.domain.model.LabeledData;
import at.fhv.layblar.events.LabeledDataAddedEvent;
import at.fhv.layblar.events.LabeledDataEvent;
import at.fhv.layblar.events.LabeledDataRemovedEvent;
import at.fhv.layblar.events.LabeledDataUpdatedEvent;
import at.fhv.layblar.utils.EntityBuilder;
import at.fhv.layblar.utils.exceptions.LabelNotFoundException;
import at.fhv.layblar.utils.exceptions.LabeledDataAlreadyRemovedException;
import at.fhv.layblar.utils.exceptions.LabeledDataNotFoundException;
import at.fhv.layblar.utils.exceptions.NotAuthorizedException;
import at.fhv.layblar.utils.exceptions.ProjectValidityTimeframeException;
import at.fhv.layblar.utils.exceptions.VersionNotMatchingException;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class LabeledDataServiceImpl implements LabeledDataService {

    @Inject
    JsonWebToken jsonWebToken;

    @Override
    public List<LabeledDataDTO> getLabeledDataByHousehold(String householdId, String projectId) throws NotAuthorizedException {
        validateHouseholdId(householdId);
        List<LabeledData> data = new LinkedList<>();
        if(projectId == null){
            data = LabeledData.list("householdId", householdId);
            return data.stream().map(labeleData -> LabeledDataDTO.createLabeledDataDTO(labeleData)).collect(Collectors.toList());
        }
        data = LabeledData.list("householdId = ?1 and projectId = ?2", householdId, projectId);
        return data.stream().map(labeleData -> LabeledDataDTO.createLabeledDataDTO(labeleData)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LabeledDataDTO addLabeledData(AddLabeledDataCommand command) throws NotAuthorizedException, ProjectValidityTimeframeException, LabelNotFoundException {
        validateHouseholdId(command.householdId);
        LabeledData labeledData = new LabeledData();
        LabeledDataAddedEvent event = labeledData.process(command);
        event.persist();
        labeledData.apply(event);
        return LabeledDataDTO.createLabeledDataDTO(labeledData);
    }

    @Override
    @Transactional
    public LabeledDataDTO updateLabeledData(UpdateLabeledDataCommand command) throws NotAuthorizedException, LabeledDataNotFoundException, LabeledDataAlreadyRemovedException, ProjectValidityTimeframeException, VersionNotMatchingException, LabelNotFoundException {
        List<LabeledDataEvent> data = getEventsByEntityId(command.labeledDataId);
        if(data.size() == 0){
            throw new LabeledDataNotFoundException();
        }
        LabeledData labeledData = EntityBuilder.buildLabeledDataEntity(data);
        validateHouseholdId(labeledData.householdId);
        LabeledDataUpdatedEvent event = labeledData.process(command);
        checkForVersionMismatch(data, labeledData);
        event.persist();
        labeledData.apply(event);
        return LabeledDataDTO.createLabeledDataDTO(labeledData);
    }

    @Override
    @Transactional
    public LabeledDataDTO deleteLabeledData(String labeledDataId) throws NotAuthorizedException, LabeledDataNotFoundException, VersionNotMatchingException {
        List<LabeledDataEvent> data = getEventsByEntityId(labeledDataId);
        if(data.size() == 0){
            throw new LabeledDataNotFoundException();
        }
        LabeledData labeledData = EntityBuilder.buildLabeledDataEntity(data);
        validateHouseholdId(labeledData.householdId);
        LabeledDataRemovedEvent event = labeledData.process(RemoveLabeledDataCommand.create(labeledDataId));
        checkForVersionMismatch(data, labeledData);
        event.persist();
        labeledData.apply(event);
        return LabeledDataDTO.createLabeledDataDTO(labeledData);
    }

    @Inject
    @Channel("label-event")
    @Broadcast
    Emitter<LabelEventDTO> emitter;

    @Override
    public void sendLabelEvent(LabelEventDTO eventDTO) throws NotAuthorizedException, ProjectValidityTimeframeException {
        validateHouseholdId(eventDTO.householdId);
        OutgoingKafkaRecordMetadata<?> metaData = OutgoingKafkaRecordMetadata.builder()
            .withTopic("label-event-" + eventDTO.projectId).build();
        emitter.send(Message.of(eventDTO).addMetadata(metaData));
    }

    private void validateHouseholdId(String householdId) throws NotAuthorizedException {
        if(!jsonWebToken.containsClaim("householdId") || !jsonWebToken.getClaim("householdId").equals(householdId)){
            throw new NotAuthorizedException("Users not authorized to do this action");
        }
    }

    private List<LabeledDataEvent> getEventsByEntityId(String entittyId) {
        return LabeledDataEvent.find("entityId", entittyId).list();
    }

    private void checkForVersionMismatch(List<LabeledDataEvent> events, LabeledData LabeledDataEvent) throws VersionNotMatchingException {
        if(events.size() != getEventsByEntityId(LabeledDataEvent.labeledDataId).size()){
            throw new VersionNotMatchingException();
        }
    }
    
}
