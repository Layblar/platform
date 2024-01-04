package at.fhv.layblar.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.Household;

public class HouseholdDTO {

    public String householdId;
    public List<HouseholdUserDTO> users;
    public List<HouseholdDeviceDTO> devices;
    public List<SmartMeterDTO> smartMeters;

    public static HouseholdDTO createHouseholdDTO(Household household) {
        HouseholdDTO householdDTO = new HouseholdDTO();
        householdDTO.householdId = household.householdId;
        householdDTO.users = household.users.stream().map(user -> HouseholdUserDTO.createUserDTO(user)).collect(Collectors.toList());
        householdDTO.devices = household.devices.stream().map(device -> HouseholdDeviceDTO.createDeviceDTO(device)).collect(Collectors.toList());
        householdDTO.smartMeters = household.smartMeters.stream().map(smartMeter -> SmartMeterDTO.createSmartMeterDTO(smartMeter)).collect(Collectors.toList());
        return householdDTO;
    }    

}
