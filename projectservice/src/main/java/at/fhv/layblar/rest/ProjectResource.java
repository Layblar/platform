package at.fhv.layblar.rest;

import at.fhv.layblar.application.ProjectService;
import at.fhv.layblar.commands.CreateProjectCommand;
import at.fhv.layblar.commands.JoinProjectCommand;
import at.fhv.layblar.commands.RegisterResearcherCommand;
import at.fhv.layblar.commands.UpdateProjectCommand;
import at.fhv.layblar.domain.model.MetaDataTemplate;
import at.fhv.layblar.utils.ResponseExceptionBuilder;
import at.fhv.layblar.utils.exceptions.ResponseException;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Authenticated
@Path("/api")
public class ProjectResource {

    @Inject
    ProjectService projectService;

    @POST
    @Path("/researcher")
    public Response registerResearcher(RegisterResearcherCommand command){
        return Response.ok().entity(projectService.createResearcher(command)).build();
    }

    @POST
    @Path("/project")
    @RolesAllowed("Researcher")
    public Response createProject(CreateProjectCommand command){
        try {
            return Response.ok().entity(projectService.createProject(command)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @PUT
    @Path("/project/{projectId}")
    @RolesAllowed("Researcher")
    public Response updateProject(@PathParam("projectId") String projectId, UpdateProjectCommand command){
        try {
            return Response.ok().entity(projectService.updateProject(projectId, command)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @GET
    @Path("/project/{projectId}")
    public Response getProject(@PathParam("projectId") String projectId){
        try {
            return Response.ok().entity(projectService.getProjectInfo(projectId)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @GET
    @Path("/project/{projectId}/data")
    @RolesAllowed("Researcher")
    public Response getProjectData(@PathParam("projectId") String projectId, 
    @DefaultValue("1") @QueryParam("pageIndex") Integer pageIndex, 
    @DefaultValue("1000") @QueryParam("pageSize")  Integer pageSize){
        try {
            return Response.ok().entity(projectService.getProjectData(projectId, pageIndex, pageSize)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @GET
    @Path("/project/{projectId}/household/{householdId}")
    public Response getProjectHouseholdMetadata(@PathParam("projectId") String projectId, @PathParam("houesholdId") String householdId){
        try {
            return Response.ok().entity(projectService.getProjectDagetProjectHouseholdMetadatata(projectId, householdId)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @GET
    @Path("/project")
    public Response listProjects(@QueryParam("researcherId") String reasearcherId, @QueryParam("householdId") String householdId, @QueryParam("isJoinable") Boolean isJoinable){
        try {
            return Response.ok().entity(projectService.getProjects(reasearcherId, householdId, isJoinable)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @POST
    @Path("/project/{projectId}/household/{householdId}")
    public Response joinProject(@PathParam("projectId") String projectId, @PathParam("householdId") String householdId, JoinProjectCommand command){
        try {
            return Response.ok().entity(projectService.joinProject(projectId, householdId, command)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @GET
    @Path("/metadata")
    public Response listMetaData(){
        return Response.ok().entity(MetaDataTemplate.listAll()).build();
    }

}
