package at.fhv.layblar.application;

import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.Household;

public class HouseholdDTO {

    public String householdId;
    public List<HouseholdUserDTO> users;
    public List<HouseholdDeviceDTO> devices;

    public static HouseholdDTO createHouseholdDTO(Household household) {
        HouseholdDTO householdDTO = new HouseholdDTO();
        householdDTO.householdId = household.householdId;
        householdDTO.users = household.users.stream().map(user -> HouseholdUserDTO.createUserDTO(user)).collect(Collectors.toList());
        householdDTO.devices = household.devices.stream().map(device -> HouseholdDeviceDTO.createDeviceDTO(device)).collect(Collectors.toList());
        return householdDTO;
    }

    @Override
    public String toString() {
        return "HouseholdDTO [householdId=" + householdId + ", users=" + users + ", devices=" + devices + "]";
    }

    

}
