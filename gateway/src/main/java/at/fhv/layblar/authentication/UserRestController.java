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
import at.fhv.layblar.authentication.dto.AccountLoginDTO;
import at.fhv.layblar.authentication.dto.AccountTokenDTO;
import at.fhv.layblar.authentication.dto.RegisterHouseholdUserDTO;
import at.fhv.layblar.authentication.dto.RegisterResearcherDTO;
import at.fhv.layblar.authentication.dto.AccountDTO;
import at.fhv.layblar.authentication.model.LayblarAccount;
import at.fhv.layblar.authentication.service.RegistrationService;
import at.fhv.layblar.authentication.service.TokenGenerator;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
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
    TokenGenerator tokenGenerator;

    @Inject
    RegistrationService registrationService;

    @POST
    @Path("/register/householduser")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = AccountDTO.class)), description = "The registered user", responseCode = "201")
    @Operation(summary = "Register a new Household user", description = "Creates a new user and assigns them to a new Household")
    @SecurityRequirement(name = "none")
    public Response createUser(
            @Parameter(description = "The user object based on the RegisterUserDTO that should be registered", required = true) RegisterHouseholdUserDTO registerHouseholdUserDTO) {
                
        return registrationService.registerHouseholdUser(registerHouseholdUserDTO);
    }

    @POST
    @Path("/register/researcher")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = AccountDTO.class)), description = "The registered researcher", responseCode = "201")
    @Operation(summary = "Register a new researcher", description = "Creates a new researcher")
    @SecurityRequirement(name = "none")
    public Response createResearcher(
            @Parameter(description = "The researcher object based on the ResearcherDTO that should be created", required = true) RegisterResearcherDTO registerResearcherDTO) {
        return registrationService.registerResearcher(registerResearcherDTO);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = AccountTokenDTO.class)), description = "The logged in user", responseCode = "200")
    @Operation(summary = "Login", description = "Login with given credentials")
    @SecurityRequirement(name = "none")
    public Response loginUser(
            @Parameter(description = "The user object based on the LoginUserDTO that should be logged in", required = true) AccountLoginDTO loginUserDTO) {
        List<LayblarAccount> layblarUser = LayblarAccount.find("email", loginUserDTO.email).list();
        if(layblarUser.size() != 1 || !BcryptUtil.matches(loginUserDTO.password, layblarUser.get(0).password)){
            return Response.status(401).entity("Email or password was wrong").build();
        }
        return Response.status(200).entity(tokenGenerator.generateToken(layblarUser.get(0))).build();
    }

}
