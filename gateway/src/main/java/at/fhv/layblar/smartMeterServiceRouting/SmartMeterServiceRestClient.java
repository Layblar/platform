package at.fhv.layblar.smartMeterServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "smart-meter-service-api")
@RegisterClientHeaders
public interface SmartMeterServiceRestClient {

    @GET
    @Path("/household/{householdId}")
    Uni<Response> getSmartMeterData(@PathParam("householdId") String householdId, @QueryParam("from") String from,
            @QueryParam("to") String to);

}
