package at.fhv.layblar.smartMeterServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "smart-meter-service-api")
@RegisterClientHeaders
public interface SmartMeterServiceRestClient {

    @Path("household/{householdId}")
    Uni<Response> getSmartMeterData(String householdId, String from, String to);
    
}
