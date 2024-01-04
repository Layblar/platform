package at.fhv.layblar.application;

import java.time.LocalDateTime;
import java.util.List;
import org.eclipse.microprofile.jwt.JsonWebToken;

import at.fhv.layblar.application.dto.HouseholdDTO;
import at.fhv.layblar.application.dto.HouseholdDeviceDTO;
import at.fhv.layblar.application.dto.HouseholdJoinCodeDTO;
import at.fhv.layblar.commands.AddDeviceCommand;
import at.fhv.layblar.commands.CreateHouseholdCommand;
import at.fhv.layblar.commands.JoinHouseholdCommand;
import at.fhv.layblar.commands.LeaveHouseholdCommand;
import at.fhv.layblar.commands.RegisterSmartMeterCommand;
import at.fhv.layblar.commands.RemoveDeviceCommand;
import at.fhv.layblar.commands.RemoveSmartMeterCommand;
import at.fhv.layblar.commands.UpdateDeviceCommand;
import at.fhv.layblar.domain.Household;
import at.fhv.layblar.domain.HouseholdJoinCode;
import at.fhv.layblar.events.DeviceAddedEvent;
import at.fhv.layblar.events.DeviceRemovedEvent;
import at.fhv.layblar.events.DeviceUpdatedEvent;
import at.fhv.layblar.events.Event;
import at.fhv.layblar.events.HouseholdCreatedEvent;
import at.fhv.layblar.events.HouseholdUserJoinedEvent;
import at.fhv.layblar.events.HouseholdUserLeftEvent;
import at.fhv.layblar.utils.EntityBuilder;
import at.fhv.layblar.utils.exceptions.DeviceNotFoundException;
import at.fhv.layblar.utils.exceptions.HouseholdNotFoundException;
import at.fhv.layblar.utils.exceptions.JoinHouseholdException;
import at.fhv.layblar.utils.exceptions.NotAuthorizedException;
import at.fhv.layblar.utils.exceptions.VersionNotMatchingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class HouseholdServiceImpl implements HouseholdService {

    @Inject
    JsonWebToken jsonWebToken;

    @Override
    @Transactional
    public HouseholdDTO createHousehold(CreateHouseholdCommand createHouseholdCommand) {
        Household household = new Household();
        HouseholdCreatedEvent event = household.process(createHouseholdCommand);
        event.persist();
        household.apply(event);
        return HouseholdDTO.createHouseholdDTO(household);
    }

    @Override
    @Transactional
    public HouseholdDTO joinHousehold(String householdId, JoinHouseholdCommand command) throws JoinHouseholdException, HouseholdNotFoundException, VersionNotMatchingException, NotAuthorizedException {
        if(!householdId.equals(command.householdId)){
            throw new NotAuthorizedException("Not authorized to join this Household");
        }
        validateUserId(command.userId);
        List<Event> oldHouseholdEvents = getEventsByEntityId(jsonWebToken.getClaim("householdId"));
        if(oldHouseholdEvents.size() == 0){
            throw new HouseholdNotFoundException("The current household was not found");
        }
        List<HouseholdJoinCode> joinCodes = HouseholdJoinCode.list("householdId = ?1 and joinCode = ?2 and validTill >= ?3", command.householdId, command.joinCode, LocalDateTime.now());
        if(joinCodes.isEmpty()){
            throw new JoinHouseholdException(404, "The JoinCode has expired");
        }
        List<Event> newHouseholdEvents = getEventsByEntityId(joinCodes.get(0).householdId);
        if(newHouseholdEvents.size() == 0){
            throw new HouseholdNotFoundException("The household to join was not found");
        }
        Household oldHousehold = EntityBuilder.buildEntity(oldHouseholdEvents);
        HouseholdUserLeftEvent householdLeftEvent = oldHousehold.process(LeaveHouseholdCommand.create(jsonWebToken.getClaim("householdId"), jsonWebToken.getSubject()));
        checkForVersionMismatch(oldHouseholdEvents, oldHousehold);
        householdLeftEvent.persist();
        oldHousehold.apply(householdLeftEvent);
        Household newHousehold = EntityBuilder.buildEntity(newHouseholdEvents);
        HouseholdUserJoinedEvent householdJoinedEvent = newHousehold.process(command);
        checkForVersionMismatch(newHouseholdEvents, newHousehold);
        householdJoinedEvent.persist();
        newHousehold.apply(householdJoinedEvent);
        return HouseholdDTO.createHouseholdDTO(newHousehold);
    }

    @Override
    @Transactional
    public HouseholdDTO leaveHousehold(String householdId, LeaveHouseholdCommand command) throws NotAuthorizedException, HouseholdNotFoundException, VersionNotMatchingException {
        if(!householdId.equals(command.householdId)){
            throw new NotAuthorizedException("Not authorized");
        }
        validateHouseholdId(householdId);
        validateUserId(command.userId);
        List<Event> events = getEventsByEntityId(householdId);
        if(events.size() == 0){
            throw new HouseholdNotFoundException("The household was not found");
        }
        Household household = EntityBuilder.buildEntity(events);
        HouseholdUserLeftEvent event = household.process(command);
        checkForVersionMismatch(events, household);
        event.persist();
        household.apply(event);
        return HouseholdDTO.createHouseholdDTO(household);
        
    }

    @Override
    public HouseholdDTO getHouseholdInformation(String householdId) throws NotAuthorizedException {
        validateHouseholdId(householdId);
        return HouseholdDTO.createHouseholdDTO(EntityBuilder.buildEntity(getEventsByEntityId(householdId)));
    }

    @Override
    @Transactional
    public HouseholdJoinCodeDTO getHouseholdJoinCodeInformation(String householdId) throws NotAuthorizedException {
        validateHouseholdId(householdId);
        List<HouseholdJoinCode> joinCodes = HouseholdJoinCode.list("householdId =?1 and validTill > ?2", householdId, LocalDateTime.now());
        if(joinCodes.isEmpty()){
            HouseholdJoinCode joinCode = HouseholdJoinCode.create(householdId);
            joinCode.persist();
            return HouseholdJoinCodeDTO.createHouseholdJoinCodeDTO(joinCode);
        } else {
            return HouseholdJoinCodeDTO.createHouseholdJoinCodeDTO(joinCodes.get(0));
        } 
    }

    @Override
    @Transactional
    public HouseholdDTO registerSmartMeter(String householdId, RegisterSmartMeterCommand command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerSmartMeter'");
    }

    @Override
    @Transactional
    public HouseholdDTO removeSmartMeter(String householdId, RemoveSmartMeterCommand command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeSmartMeter'");
    }

    @Override
    @Transactional
    public HouseholdDTO addDeviceToHousehold(String householdId, AddDeviceCommand command) throws VersionNotMatchingException, HouseholdNotFoundException, NotAuthorizedException {
        validateHouseholdId(householdId);
        List<Event> events = getEventsByEntityId(householdId);
        if(events.size() == 0){
            throw new HouseholdNotFoundException("The household was not found");
        }
        Household household = EntityBuilder.buildEntity(events);
        DeviceAddedEvent event = household.process(command);
        checkForVersionMismatch(events, household);
        event.persist();
        household.apply(event);
        return HouseholdDTO.createHouseholdDTO(household);
    }

    @Override
    @Transactional
    public HouseholdDTO updateDeviceInformation(String householdId, UpdateDeviceCommand command) throws VersionNotMatchingException, HouseholdNotFoundException, NotAuthorizedException, DeviceNotFoundException {
        validateHouseholdId(householdId);
        List<Event> events = getEventsByEntityId(householdId);
        if(events.size() == 0){
            throw new HouseholdNotFoundException("The household was not found");
        }
        Household household = EntityBuilder.buildEntity(events);
        DeviceUpdatedEvent event = household.process(command);
        checkForVersionMismatch(events, household);
        event.persist();
        household.apply(event);
        return HouseholdDTO.createHouseholdDTO(household);
    }

    @Override
    @Transactional
    public HouseholdDTO removeDeviceFromHousehold(String householdId, RemoveDeviceCommand command) throws VersionNotMatchingException, NotAuthorizedException, HouseholdNotFoundException, DeviceNotFoundException {
        validateHouseholdId(householdId);
        List<Event> events = getEventsByEntityId(householdId);
        if(events.size() == 0){
            throw new HouseholdNotFoundException("The household was not found");
        }
        Household household = EntityBuilder.buildEntity(events);
        DeviceRemovedEvent event = household.process(command);
        checkForVersionMismatch(events, household);
        event.persist();
        household.apply(event);
        return HouseholdDTO.createHouseholdDTO(household);
    }

    @Override
    public List<HouseholdDeviceDTO> getHouseholdDevices(String householdId) throws NotAuthorizedException {
        validateHouseholdId(householdId);
        return HouseholdDTO.createHouseholdDTO(EntityBuilder.buildEntity(getEventsByEntityId(householdId))).devices;
    }

    private void validateHouseholdId(String householdId) throws NotAuthorizedException {
        if(!jsonWebToken.getClaim("householdId").equals(householdId)){
            throw new NotAuthorizedException("Users not authorized to do this action");
        }
    }

    private void validateUserId(String userId) throws NotAuthorizedException {
        if(!jsonWebToken.getSubject().equals(userId)){
            throw new NotAuthorizedException("Users not authorized to do this action");
        }
    }

    private List<Event> getEventsByEntityId(String entittyId) {
        return Event.find("entityId", entittyId).list();
    }

    private void checkForVersionMismatch(List<Event> events, Household household) throws VersionNotMatchingException {
        if(events.size() != getEventsByEntityId(household.householdId).size()){
            throw new VersionNotMatchingException();
        }
    }

    
}
