package at.fhv.layblar.labelServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.fhv.layblar.labelServiceRouting.model.AddLabeledDataDTO;
import at.fhv.layblar.labelServiceRouting.model.LabelEventDTO;
import at.fhv.layblar.labelServiceRouting.model.UpdateLabeledDataDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "label-service-api")
@RegisterClientHeaders
@Path("/label")
public interface LabelServiceRestClient {

    @GET
    @Path("/{householdId}")
    public Uni<Response> getLabeledDatasByHousehold(@PathParam("householdId") String householdId, @QueryParam("projectId") String projectId);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> addLabeledData(AddLabeledDataDTO command);

    @POST
    @Path("/event")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> addLabelEvent(LabelEventDTO eventDTO);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{labeledDataId}")
    public Uni<Response> updateLabeledData(@PathParam("labeledDataId") String labeledDataId, UpdateLabeledDataDTO command);

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{labeledDataId}")
    public Uni<Response> deleteLabeledData(@PathParam("labeledDataId") String labeledDataId);
}
