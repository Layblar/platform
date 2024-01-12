package at.fhv.layblar.householdServiceRouting.model;

import at.fhv.layblar.authentication.dto.RegisterHouseholdUserDTO;

public class CreateHouseholdDTO {

    public String email;
    public String firstName;
    public String lastName;

    public static CreateHouseholdDTO createHouseholdDTO(RegisterHouseholdUserDTO registerUserDTO){
        CreateHouseholdDTO createHouseholdDTO = new CreateHouseholdDTO();
        createHouseholdDTO.email = registerUserDTO.email;
        createHouseholdDTO.firstName = registerUserDTO.firstName;
        createHouseholdDTO.lastName = registerUserDTO.lastName;
        return createHouseholdDTO;
    }
    
}
