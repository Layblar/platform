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

import at.fhv.layblar.projectServiceRouting.model.CreateProjectDTO;
import at.fhv.layblar.projectServiceRouting.model.JoinProjectDTO;
import at.fhv.layblar.projectServiceRouting.model.MetaDataTemplateDTO;
import at.fhv.layblar.projectServiceRouting.model.ProjectDataDTO;
import at.fhv.layblar.projectServiceRouting.model.ProjectInfoDTO;
import at.fhv.layblar.projectServiceRouting.model.ProjectMetaDataDTO;
import at.fhv.layblar.projectServiceRouting.model.UpdateProjectDTO;
import io.quarkus.security.Authenticated;
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

@Authenticated
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
    @Path("/project")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectInfoDTO.class)), description = "The created project", responseCode = "200")
    @Operation(summary = "Create a Project", description = "Create a new Project")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> createProject(
            @Parameter(description = "The project object based on the ProjectDTO that should be created", required = true) CreateProjectDTO project) {
        return restClient.createProject(project);
    }

    @PUT
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectInfoDTO.class)), description = "The updated project", responseCode = "200")
    @Operation(summary = "Update a Project", description = "Update a Project")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> updateProject(
            @Parameter(description = "The ID of the project to update", required = true) @PathParam("projectId") String projectId,
            @Parameter(description = "The project object based on the ProjectDTO that should be updated", required = true) UpdateProjectDTO project) {
        return restClient.updateProject(projectId, project);
    }

    @GET
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectInfoDTO.class)), description = "Project by id", responseCode = "200")
    @Operation(summary = "Get a Project", description = "Get a Project")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> getProject(
            @Parameter(description = "The ID of the project to get", required = true) @PathParam("projectId") String projectId) {
        return restClient.getProject(projectId);
    }

    @GET
    @Path("/project/{projectId}/data")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectDataDTO.class)), description = "Project data by id", responseCode = "200")
    @Operation(summary = "Get data from a Project", description = "Get data from a Project")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> getProjectData(
            @Parameter(description = "The ID of the project to get data from", required = true) @PathParam("projectId") String projectId) {
        return restClient.getProjectData(projectId);
    }

    @GET
    @Path("/project")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = ProjectInfoDTO.class)), description = "List of all projects", responseCode = "200")
    @Operation(summary = "List all Projects", description = "List all Projects")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> listProjects() {
        return restClient.listProjects();
    }

    @POST
    @Path("/project/{projectId}/household/{householdId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectInfoDTO.class)), description = "The joined Project Information", responseCode = "200")
    @Operation(summary = "Join a Project", description = "Join a Project")
    @SecurityRequirement(name = "jwt")
        Uni<Response> joinProject(
            @Parameter(description = "The ID of the project to join", required = true) @PathParam("projectId") String projectId,
            @Parameter(description = "The ID of the household that wants to join the project", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "Household meta data and a list of devices", required = true) JoinProjectDTO joinProjectDTO){

        return restClient.joinProject(projectId, householdId, joinProjectDTO);
    }

    @GET
    @Path("/project/{projectId}/household/{householdId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ProjectMetaDataDTO.class)), description = "Household metadata for a project by id", responseCode = "200")
    @Operation(summary = "Get household metadata from a Project", description = "Get household metadata from a Project")
    @SecurityRequirement(name = "jwt")
    public Uni<Response>  getProjectHouseholdMetadata(@Parameter(description = "The ID of the project", required = true) @PathParam("projectId") String projectId,
    @Parameter(description = "The ID of the household ", required = true) @PathParam("householdId") String householdId){
        return restClient.getProjectHouseholdMetadata(projectId, householdId);
    }

    @GET
    @Path("/metadata")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = MetaDataTemplateDTO.class)), description = "List of Metadata Templates", responseCode = "200")
    @Operation(summary = "Metadata template list", description = "Get the list of all available Metadata templates")
    @SecurityRequirement(name = "jwt")
    public Uni<Response>  listMetaData(){
        return restClient.listMetaData();
    }
}
