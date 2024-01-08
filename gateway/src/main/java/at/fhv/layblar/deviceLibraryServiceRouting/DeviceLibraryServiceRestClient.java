package at.fhv.layblar.deviceLibraryServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "device-library-service-api")
@RegisterClientHeaders
public interface DeviceLibraryServiceRestClient {

    @GET
    public Uni<Response> listDevices(@QueryParam("name") String name);

    @GET
    @Path("/{deviceId}")
    public Uni<Response> getDeviceById(@PathParam("deviceId") String deviceId);

    @GET
    @Path("/category")
    public Uni<Response> listCategoires(@QueryParam("name") String name);

    @GET
    @Path("/category/{categoryId}")
    public Uni<Response> getCategoryById(@PathParam("categoryId") String categoryId);

}
