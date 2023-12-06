package at.fhv.layblar.authentication;

import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.authentication.model.LayblarUser;
import at.fhv.layblar.authentication.model.LoginUserDTO;
import at.fhv.layblar.authentication.model.RegisterUserDTO;
import at.fhv.layblar.userServiceRouting.HouseholdServiceRestClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/auth")
@Tag(name = "Authorization", description = "Authorizaton operations. Use these Endpoints to get a valid JWT for the Layblar Platform.")
@APIResponse(responseCode = "401", description = "Unauthorized")
@APIResponse(responseCode = "403", description = "Invalid User")
@APIResponse(responseCode = "500", description = "Server Error")
public class UserRestController {

    @Inject
    @RestClient
    HouseholdServiceRestClient restClient;

    @Inject
    TokenGenerator tokenGenerator;

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = RegisterUserDTO.class)), description = "The registered user", responseCode = "200")
    @Operation(summary = "Register a new UserAccount", description = "Creates a new user and assigns them to a new Household")
    @SecurityRequirement(name = "none")
    public Response createUser(
            @Parameter(description = "The user object based on the RegisterUserDTO that should be registered", required = true) RegisterUserDTO registerUserDTO) {
                
        List<LayblarUser> layblarUser = LayblarUser.find("email", registerUserDTO.email).list();
        if(layblarUser.size() != 0){
            return Response.status(403).entity("Username not valid").build();
        }
        LayblarUser user = new LayblarUser();
        user.userId = UUID.randomUUID().toString();
        user.password = "TEST";
        //LayblarUser user = (LayblarUser) restClient.createHousehold(registerUserDTO).await().indefinitely().getEntity();
        user.persist();
        return Response.status(204).entity(user).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = LoginUserDTO.class)), description = "The logged in user", responseCode = "200")
    @Operation(summary = "Login", description = "Login with given credentials")
    @SecurityRequirement(name = "none")
    public Response loginUser(
            @Parameter(description = "The user object based on the LoginUserDTO that should be logged in", required = true) LoginUserDTO loginUserDTO) {
        List<LayblarUser> layblarUser = LayblarUser.find("username", loginUserDTO.username).list();
        if(layblarUser.size() != 1 || !layblarUser.get(0).password.equals(loginUserDTO.password)){
            return Response.status(401).entity("Not Authorized").build();
        }
        return Response.status(200).entity(tokenGenerator.generateToken(layblarUser.get(0))).build();
    }

}
