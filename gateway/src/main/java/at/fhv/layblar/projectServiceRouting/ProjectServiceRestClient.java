package at.fhv.layblar.projectServiceRouting;

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

@RegisterRestClient(configKey = "project-service-api")
@RegisterClientHeaders
public interface ProjectServiceRestClient {

    @POST
    @Path("/researcher")
    Uni<Response> createResearcher(ResearcherDTO researcher);

    @POST
    @Path("/project")
    Uni<Response> createProject(ProjectDTO project);

    @PUT
    @Path("/project/{projectId}")
    Uni<Response> updateProject(@PathParam("projectId") String projectId, ProjectDTO project);

    @GET
    @Path("/project/{projectId}")
    Uni<Response> getProject(@PathParam("projectId") String projectId);

    @GET
    @Path("/project")
    Uni<Response> listProjects();

    @POST
    @Path("/project/{projectId}/join/{userId}")
    Uni<Response> joinProject(@PathParam("projectId") String projectId, @PathParam("userId") String userId);

}
