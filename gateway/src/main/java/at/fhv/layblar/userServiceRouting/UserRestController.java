package at.fhv.layblar.userServiceRouting;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.userServiceRouting.model.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

//@Authenticated
@Path("user")
@Tag(name = "User", description = "User Operations")
@APIResponse(responseCode = "401", description = "Unauthorized")
@APIResponse(responseCode = "403", description = "Invalid User")
@APIResponse(responseCode = "500", description = "Server Error")
public class UserRestController {

    @Inject
    @RestClient
    UserServiceRestClient restClient;

    @POST
    @Path("/register")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = UserDTO.class)), description = "Smart-Meter data for the specified time period", responseCode = "200")
    @Operation(summary = "Register a new UserAccount", description = "Creates a new user and assigns them to a new Household")
    @SecurityRequirement(name = "jwt") // || none
    Uni<Response> createUser(UserDTO user){
        return restClient.createUser(user);
    }

    @POST
    @Path("/login")
    Uni<Response> login(UserDTO user){
        return restClient.login(user);
    }

    @POST
    @Path("/logout")
    Uni<Response> logout(){
        return restClient.logout();
    }
    
}
