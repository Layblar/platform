package at.fhv.layblar.application;

import java.util.List;

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
import at.fhv.layblar.utils.exceptions.DeviceNotFoundException;
import at.fhv.layblar.utils.exceptions.HouseholdNotFoundException;
import at.fhv.layblar.utils.exceptions.JoinHouseholdException;
import at.fhv.layblar.utils.exceptions.NotAuthorizedException;
import at.fhv.layblar.utils.exceptions.VersionNotMatchingException;

public interface HouseholdService {
    
    public HouseholdDTO createHousehold(CreateHouseholdCommand command);

    public HouseholdDTO joinHousehold(String householdId, JoinHouseholdCommand command) throws NotAuthorizedException, VersionNotMatchingException, JoinHouseholdException, HouseholdNotFoundException;

    public HouseholdDTO leaveHousehold(String householdId, LeaveHouseholdCommand command) throws NotAuthorizedException, VersionNotMatchingException, HouseholdNotFoundException;

    public HouseholdDTO getHouseholdInformation(String houesholdId) throws NotAuthorizedException;

    public HouseholdJoinCodeDTO getHouseholdJoinCodeInformation(String householdId) throws NotAuthorizedException;

    public HouseholdDTO registerSmartMeter(String householdId, RegisterSmartMeterCommand command) throws NotAuthorizedException, VersionNotMatchingException;

    public HouseholdDTO removeSmartMeter(String householdId, RemoveSmartMeterCommand command) throws NotAuthorizedException, VersionNotMatchingException;

    public HouseholdDTO addDeviceToHousehold(String householdId, AddDeviceCommand command) throws NotAuthorizedException, VersionNotMatchingException, HouseholdNotFoundException;

    public HouseholdDTO updateDeviceInformation(String householdId, UpdateDeviceCommand command) throws NotAuthorizedException, VersionNotMatchingException, HouseholdNotFoundException, DeviceNotFoundException;

    public HouseholdDTO removeDeviceFromHousehold(String householdId, RemoveDeviceCommand command) throws NotAuthorizedException, VersionNotMatchingException, HouseholdNotFoundException, DeviceNotFoundException;

    public List<HouseholdDeviceDTO> getHouseholdDevices(String householdId) throws NotAuthorizedException;
}
