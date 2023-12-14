package at.fhv.layblar.authentication;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.authentication.dto.CreateHouseholdDTO;
import at.fhv.layblar.authentication.dto.LoginUserDTO;
import at.fhv.layblar.authentication.dto.RegisterUserDTO;
import at.fhv.layblar.authentication.dto.TokenDTO;
import at.fhv.layblar.authentication.dto.UserDTO;
import at.fhv.layblar.authentication.model.LayblarAccount;
import at.fhv.layblar.authentication.service.TokenGenerator;
import at.fhv.layblar.householdServiceRouting.HouseholdServiceRestClient;
import at.fhv.layblar.householdServiceRouting.model.HouseholdDTO;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Tag(name = "Authorization", description = "Authorizaton operations. Use these Endpoints to get a valid JWT for the Layblar Platform.")
@APIResponse(responseCode = "401", description = "Unauthorized")
@APIResponse(responseCode = "403", description = "Invalid User")
@APIResponse(responseCode = "500", description = "Server Error")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class UserRestController {

    @Inject
    @RestClient
    HouseholdServiceRestClient restClient;

    @Inject
    TokenGenerator tokenGenerator;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = UserDTO.class)), description = "The registered user", responseCode = "201")
    @Operation(summary = "Register a new UserAccount", description = "Creates a new user and assigns them to a new Household")
    @SecurityRequirement(name = "none")
    @Transactional
    public Response createUser(
            @Parameter(description = "The user object based on the RegisterUserDTO that should be registered", required = true) RegisterUserDTO registerUserDTO) {
                
        List<LayblarAccount> layblarAccount = LayblarAccount.find("email", registerUserDTO.email).list();
        if(layblarAccount.size() != 0){
            return Response.status(403).entity("You cannot create an Account with this Email-Address").build();
        }
        CreateHouseholdDTO createHouseholdDTO = CreateHouseholdDTO.createHouseholdDTO(registerUserDTO);
        HouseholdDTO household = restClient.createHousehold(createHouseholdDTO, tokenGenerator.generateRegistrationToken()).await().indefinitely().readEntity(HouseholdDTO.class);
        LayblarAccount user = LayblarAccount.createUser(household.users.get(0).userId, household.users.get(0).email, registerUserDTO.password, household.householdId, registerUserDTO.roles);
        user.persist();
        UserDTO userDTO = new UserDTO();
        userDTO.email = user.email;
        userDTO.householdId = user.householdId;
        userDTO.userId = user.userId;
        return Response.status(201).entity(userDTO).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = TokenDTO.class)), description = "The logged in user", responseCode = "200")
    @Operation(summary = "Login", description = "Login with given credentials")
    @SecurityRequirement(name = "none")
    public Response loginUser(
            @Parameter(description = "The user object based on the LoginUserDTO that should be logged in", required = true) LoginUserDTO loginUserDTO) {
        List<LayblarAccount> layblarUser = LayblarAccount.find("email", loginUserDTO.email).list();
        if(layblarUser.size() != 1 || !BcryptUtil.matches(loginUserDTO.password, layblarUser.get(0).password)){
            return Response.status(401).entity("Email or password was wrong").build();
        }
        return Response.status(200).entity(tokenGenerator.generateToken(layblarUser.get(0))).build();
    }

}
