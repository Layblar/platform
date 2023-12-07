package at.fhv.layblar.application;

import at.fhv.layblar.domain.User;

public class UserDTO {

    public String userId;
    public String email;
    public String firstName;
    public String lastName;

    public static UserDTO createUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.email = user.email;
        userDTO.userId = user.userId;
        userDTO.firstName = user.firstName;
        userDTO.lastName = user.lastName;
        return userDTO;
    }

}
