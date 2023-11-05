package at.fhv.layblar.deviceLibraryServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "device-library-service-api")
@RegisterClientHeaders
public interface DeviceLibraryServiceRestClient {

    @GET
    Uni<Response> listDevices();
    
}
