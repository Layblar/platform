package at.fhv.layblar.projectServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.fhv.layblar.projectServiceRouting.model.CreateProjectDTO;
import at.fhv.layblar.projectServiceRouting.model.JoinProjectDTO;
import at.fhv.layblar.projectServiceRouting.model.ResearcherDTO;
import at.fhv.layblar.projectServiceRouting.model.UpdateProjectDTO;
import io.quarkus.rest.client.reactive.NotBody;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "project-service-api")
@RegisterClientHeaders
public interface ProjectServiceRestClient {

    @POST
    @Path("/researcher")
    @ClientHeaderParam(name = "Authorization", value = "Bearer {token}")
    Uni<Response> registerResearcher(ResearcherDTO researcher, @NotBody String token);

    @POST
    @Path("/project")
    Uni<Response> createProject(CreateProjectDTO project);

    @PUT
    @Path("/project/{projectId}")
    Uni<Response> updateProject(@PathParam("projectId") String projectId, UpdateProjectDTO project);

    @GET
    @Path("/project/{projectId}")
    Uni<Response> getProject(@PathParam("projectId") String projectId);

    @GET
    @Path("/project/{projectId}/data")
    Uni<Response> getProjectData(@PathParam("projectId") String projectId);

    @GET
    @Path("/project")
    Uni<Response> listProjects();

    @POST
    @Path("/project/{projectId}/household/{householdId}")
    Uni<Response> joinProject(@PathParam("projectId") String projectId, @PathParam("householdId") String householdId, JoinProjectDTO joinProjectDTO);

}
