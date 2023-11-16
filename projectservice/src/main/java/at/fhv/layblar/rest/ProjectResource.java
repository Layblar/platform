package at.fhv.layblar.rest;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.fhv.layblar.projectServiceRouting.model.ProjectDTO;
import at.fhv.layblar.projectServiceRouting.model.ResearcherDTO;
import at.fhv.layblar.userServiceRouting.model.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/data")
public class ProjectResource {

    @POST
    @Path("/researcher}")
    Response createResearcher(ResearcherDTO researcher){
        
    }

    @POST
    @Path("/project")
    Response createProject(ProjectDTO project){

    }

    @PUT
    @Path("/project/{projectId}")
    Response updateProject(@PathParam("projectId") String projectId, ProjectDTO project){

    }

    @GET
    @Path("/project/{projectId}")
    Response getProject(@PathParam("projectId") String projectId){

    }

    @GET
    @Path("/project/{projectId}/label")
    Response getProjectLabels(@PathParam("projectId") String projectId, @QueryParam("from") String from, @QueryParam("to") String to) {

    }

    @GET
    @Path("/project")
    Response listProjects() {

    }

    @POST
    @Path("/project/{projectId}/join")
    Response joinProject(@PathParam("projectId") String projectId, UserDTO user){

    }

}
