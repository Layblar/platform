package at.fhv.layblar.authentication.dto;

public class CreateHouseholdDTO {

    public String email;
    public String firstName;
    public String lastName;

    public static CreateHouseholdDTO createHouseholdDTO(RegisterUserDTO registerUserDTO){
        CreateHouseholdDTO createHouseholdDTO = new CreateHouseholdDTO();
        createHouseholdDTO.email = registerUserDTO.email;
        createHouseholdDTO.firstName = registerUserDTO.firstName;
        createHouseholdDTO.lastName = registerUserDTO.lastName;
        return createHouseholdDTO;
    }
    
}
