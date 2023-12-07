package at.fhv.layblar.application;

import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.Household;

public class HouseholdDTO {

    public String householdId;
    public List<UserDTO> users;
    public List<DeviceDTO> devices;

    public static HouseholdDTO createHouseholdDTO(Household household) {
        HouseholdDTO householdDTO = new HouseholdDTO();
        householdDTO.householdId = household.householdId;
        householdDTO.users = household.users.stream().map(user -> UserDTO.createUserDTO(user)).collect(Collectors.toList());
        householdDTO.devices = household.devices.stream().map(device -> DeviceDTO.createDeviceDTO(device)).collect(Collectors.toList());
        return householdDTO;
    }

}
