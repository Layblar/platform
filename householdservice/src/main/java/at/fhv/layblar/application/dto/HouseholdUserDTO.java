package at.fhv.layblar.application.dto;

import at.fhv.layblar.domain.HouseholdUser;

public class HouseholdUserDTO {

    public String userId;
    public String email;
    public String firstName;
    public String lastName;

    public static HouseholdUserDTO createUserDTO(HouseholdUser user) {
        HouseholdUserDTO userDTO = new HouseholdUserDTO();
        userDTO.email = user.email;
        userDTO.userId = user.userId;
        userDTO.firstName = user.firstName;
        userDTO.lastName = user.lastName;
        return userDTO;
    }

}
