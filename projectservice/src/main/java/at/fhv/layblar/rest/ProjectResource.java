package at.fhv.layblar.rest;

import at.fhv.layblar.domain.Project;
import at.fhv.layblar.domain.Researcher;
import at.fhv.layblar.infrastructure.ProjectRepository;
import at.fhv.layblar.infrastructure.ResearcherRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/data")
public class ProjectResource {

    @Inject
    ProjectRepository projectRepository;

    @Inject
    ResearcherRepository researcherRepository;

    @POST
    @Path("/researcher")
    Response createResearcher(Researcher researcher){
        
        try {
            researcherRepository.persist(researcher);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Could not create researcher").build();
        }
    }

    @POST
    @Path("/project")
    Response createProject(Project project){
            
        try {
            projectRepository.persist(project);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Could not create project").build();
        }
    }

    @PUT
    @Path("/project/{projectId}")
    Response updateProject(@PathParam("projectId") String projectId, Project project){
        try {
            Project projectToUpdate = projectRepository.findById(projectId);
            projectToUpdate.name = project.name;
            projectToUpdate.description = project.description;
            projectToUpdate.startDate = project.startDate;
            projectToUpdate.endDate = project.endDate;
            projectToUpdate.researcherId = project.researcherId;
            projectToUpdate.participants = project.participants;
            projectToUpdate.persist();
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Could not update project").build();
        }
    }

    @GET
    @Path("/project/{projectId}")
    Response getProject(@PathParam("projectId") String projectId){
        try {
            Project project = projectRepository.findById(projectId);
            return Response.ok(project).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Could not get project").build();
        }
    }

    @GET
    @Path("/project")
    Response listProjects() {
        try {
            return Response.ok(projectRepository.listAll()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Could not list projects").build();
        }
    }

    @POST
    @Path("/project/{projectId}/join/{userId}")
    Response joinProject(@PathParam("projectId") String projectId, @PathParam("userId") String userId) {
        try {
            Project project = projectRepository.findById(projectId);
            project.participants.add(userId);
            project.persist();
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Could not join project").build();
        }
    }

}
