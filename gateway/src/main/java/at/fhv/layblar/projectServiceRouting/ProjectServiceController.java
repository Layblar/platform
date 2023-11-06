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

import at.fhv.layblar.labelServiceRouting.model.LabelDTO;
import at.fhv.layblar.projectServiceRouting.model.ProjectDTO;
import at.fhv.layblar.projectServiceRouting.model.ResearcherDTO;
import at.fhv.layblar.userServiceRouting.model.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Tag(name = "Project", description = "Operations regarding Project management")
@APIResponse(responseCode = "401", description = "Unauthorized")
@APIResponse(responseCode = "403", description = "Invalid User")
@APIResponse(responseCode = "500", description = "Server Error")
public class ProjectServiceController {

    @Inject
    @RestClient
    ProjectServiceRestClient restClient;

    @POST
    @Path("/researcher}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ResearcherDTO.class)), description = "The created researcher", responseCode = "200")
    @Operation(summary = "Create a Researcher", description = "Create a new Researcher")
    @SecurityRequirement(name = "jwt")
    Uni<Response> createResearcher(
            @Parameter(description = "The researcher object based on the ResearcherDTO that should be created", required = true) ResearcherDTO researcher) {
        return restClient.createResearcher(researcher);
    }

    @POST
    @Path("/project")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectDTO.class)), description = "The created project", responseCode = "200")
    @Operation(summary = "Create a Project", description = "Create a new Project")
    @SecurityRequirement(name = "jwt")
    Uni<Response> createProject(
            @Parameter(description = "The project object based on the ProjectDTO that should be created", required = true) ProjectDTO project) {
        return restClient.createProject(project);
    }

    @PUT
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectDTO.class)), description = "The updated project", responseCode = "200")
    @Operation(summary = "Update a Project", description = "Update a Project")
    @SecurityRequirement(name = "jwt")
    Uni<Response> updateProject(
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
    Uni<Response> getProject(
            @Parameter(description = "The ID of the project to get", required = true) @PathParam("projectId") String projectId) {
        return restClient.getProject(projectId);
    }

    @GET
    @Path("/project/{projectId}/label")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = LabelDTO.class)), description = "Labels for the specified time period for given project", responseCode = "200")
    @Operation(summary = "Get Labels for a Project", description = "Get Labels for a Project")
    @SecurityRequirement(name = "jwt")
    Uni<Response> getProjectLabels(
            @Parameter(description = "The ID of the project to get labels for", required = true) @PathParam("projectId") String projectId,
            @Parameter(description = "The begin of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = false) @QueryParam("from") String from,
            @Parameter(description = "The end of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = false) @QueryParam("to") String to) {
        return restClient.getProjectLabels(projectId, from, to);
    }

    @GET
    @Path("/project")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = ProjectDTO.class)), description = "List of all projects", responseCode = "200")
    @Operation(summary = "List all Projects", description = "List all Projects")
    @SecurityRequirement(name = "jwt")
    Uni<Response> listProjects() {
        return restClient.listProjects();
    }

    @POST
    @Path("/project/{projectId}/join")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = ProjectDTO.class)), description = "The joined Project Information", responseCode = "200")
    @Operation(summary = "Join a Project", description = "Join a Project")
    @SecurityRequirement(name = "jwt")
    Uni<Response> joinProject(
            @Parameter(description = "The ID of the project to join", required = true) @PathParam("projectId") String projectId,
            @Parameter(description = "The user object based on the UserDTO that should join the project", required = true) UserDTO user) {
        return restClient.joinProject(projectId, user);
    }
}
