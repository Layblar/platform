package at.fhv.layblar.labelServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.fhv.layblar.labelServiceRouting.model.LabelDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "label-service-api")
@RegisterClientHeaders
public interface LabelServiceRestClient {

    @GET
    @Path("/label/{labelId}")
    Uni<Response> getLabel(@PathParam("labelId") String labelId);

    @GET
    @Path("/label/project/{projectId}")
    Uni<Response> getProjectLabels(@PathParam("projectId") String projectId, @QueryParam("from") String from,
            @QueryParam("to") String to);

    @PUT
    @Path("/label")
    Uni<Response> createLabel(LabelDTO label);
}
