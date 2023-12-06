package at.fhv.layblar.projectServiceRouting;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.projectServiceRouting.model.ProjectDTO;
import at.fhv.layblar.projectServiceRouting.model.ResearcherDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@APIResponse(responseCode = "401", description = "Unauthorized")
@APIResponse(responseCode = "403", description = "Invalid User")
@APIResponse(responseCode = "500", description = "Server Error")
@Path("/project")
@Tag(name = "Project", description = "Operations regarding Project management")
public class ProjectServiceController {

    @Inject
    @RestClient
    ProjectServiceRestClient restClient;

    @POST
    @Path("/researcher")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ResearcherDTO.class)), description = "The created researcher", responseCode = "200")
    @Operation(summary = "Create a Researcher", description = "Create a new Researcher")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> createResearcher(
            @Parameter(description = "The researcher object based on the ResearcherDTO that should be created", required = true) ResearcherDTO researcher) {
        return restClient.createResearcher(researcher);
    }

    @POST
    @Path("/project")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectDTO.class)), description = "The created project", responseCode = "200")
    @Operation(summary = "Create a Project", description = "Create a new Project")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> createProject(
            @Parameter(description = "The project object based on the ProjectDTO that should be created", required = true) ProjectDTO project) {
        return restClient.createProject(project);
    }

    @PUT
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectDTO.class)), description = "The updated project", responseCode = "200")
    @Operation(summary = "Update a Project", description = "Update a Project")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> updateProject(
            @Parameter(description = "The ID of the project to update", required = true) @PathParam("projectId") String projectId,
            @Parameter(description = "The project object based on the ProjectDTO that should be updated", required = true) ProjectDTO project) {
        return restClient.updateProject(projectId, project);
    }

    @GET
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectDTO.class)), description = "Project by id", responseCode = "200")
    @Operation(summary = "Get a Project", description = "Get a Project")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> getProject(
            @Parameter(description = "The ID of the project to get", required = true) @PathParam("projectId") String projectId) {
        return restClient.getProject(projectId);
    }

    @GET
    @Path("/project")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = ProjectDTO.class)), description = "List of all projects", responseCode = "200")
    @Operation(summary = "List all Projects", description = "List all Projects")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> listProjects() {
        return restClient.listProjects();
    }

    @POST
    @Path("/project/{projectId}/join/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = ProjectDTO.class)), description = "The joined Project Information", responseCode = "200")
    @Operation(summary = "Join a Project", description = "Join a Project")
    @SecurityRequirement(name = "jwt")
        Uni<Response> joinProject(
            @Parameter(description = "The ID of the project to join", required = true) @PathParam("projectId") String projectId,
            @Parameter(description = "The ID of the user that wants to join the project", required = true) @PathParam("userId") String user){

        return restClient.joinProject(projectId, user);
    }
}
