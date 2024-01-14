package at.fhv.layblar.application;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import at.fhv.layblar.application.dto.LabeledDataDTO;
import at.fhv.layblar.commands.AddLabeledDataCommand;
import at.fhv.layblar.commands.UpdateLabeledDataCommand;
import at.fhv.layblar.domain.model.LabeledData;
import at.fhv.layblar.domain.model.Project;
import at.fhv.layblar.domain.readmodel.ProjectReadModel;
import at.fhv.layblar.events.LabeledDataAddedEvent;
import at.fhv.layblar.events.LabeledDataEvent;
import at.fhv.layblar.events.ProjectEvent;
import at.fhv.layblar.utils.EntityBuilder;
import at.fhv.layblar.utils.exceptions.NotAuthorizedException;
import at.fhv.layblar.utils.exceptions.ProjectValidityTimeframeException;
import at.fhv.layblar.utils.exceptions.VersionNotMatchingException;
import jakarta.inject.Inject;

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
    public LabeledDataDTO addLabeledData(AddLabeledDataCommand command) throws NotAuthorizedException, ProjectValidityTimeframeException {
        validateHouseholdId(command.householdId);
        Project project = EntityBuilder.buildProjectEntity(ProjectEvent.find("entityId", command.projectId).list());
        if(!project.isProjectParticipant(command.householdId)){
            throw new NotAuthorizedException("Not part of the project");
        }
        if(!project.isActive()){
            throw new ProjectValidityTimeframeException("Project is not active");
        }
        LabeledData labeledData = new LabeledData();
        LabeledDataAddedEvent event = labeledData.process(command);
        event.persist();
        labeledData.apply(event);
        return LabeledDataDTO.createLabeledDataDTO(labeledData);
    }

    @Override
    public List<LabeledDataDTO> updateLabeledData(UpdateLabeledDataCommand command) throws NotAuthorizedException {
        validateHouseholdId(command.householdId);
        throw new UnsupportedOperationException("Unimplemented method 'updateLabeledData'");
    }

    @Override
    public Object deleteLabeledData(String labeledDataId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteLabeledData'");
    }

    private void validateProjectId(ProjectReadModel project) throws NotAuthorizedException {
        if(!jsonWebToken.getClaim("researcherId").equals(project.researcherId)) {
            throw new NotAuthorizedException("Not Authorized to do this action");
        }
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
