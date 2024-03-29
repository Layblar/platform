package at.fhv.layblar.projectServiceRouting;

import java.time.LocalDateTime;

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
import jakarta.ws.rs.QueryParam;
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
    Uni<Response> getProjectData(@PathParam("projectId") String projectId, @QueryParam("labeledDataId") String labeledDataId,
    @QueryParam("validAt") LocalDateTime validAt, @QueryParam("pageIndex") Integer pageIndex, @QueryParam("pageSize") Integer pageSize);

    @GET
    @Path("/project/{projectId}/household/{householdId}")
    Uni<Response> getProjectHouseholdMetadata(@PathParam("projectId") String projectId, @PathParam("houesholdId") String householdId);

    @GET
    @Path("/project")
    Uni<Response> listProjects(@QueryParam("researcherId") String researcherId, @QueryParam("householdId") String householdId, @QueryParam("isJoinable") Boolean isJoinable);

    @POST
    @Path("/project/{projectId}/household/{householdId}")
    Uni<Response> joinProject(@PathParam("projectId") String projectId, @PathParam("householdId") String householdId, JoinProjectDTO joinProjectDTO);

    @GET
    @Path("/metadata")
    Uni<Response> listMetaData();
}
