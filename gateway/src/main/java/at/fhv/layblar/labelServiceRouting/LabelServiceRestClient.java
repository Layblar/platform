package at.fhv.layblar.labelServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.fhv.layblar.labelServiceRouting.model.CreateLabelDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "label-service-api")
@RegisterClientHeaders
public interface LabelServiceRestClient {

    @GET
    @Path("/{householdId}")
    Uni<Response> getLabelsByHousehold(@PathParam("householdId") String householdId);

    @POST
    @Path("/label")
    Uni<Response> createLabel(CreateLabelDTO createLabelDTO);
}
