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
    @Path("device")
    public Uni<Response> listDevices(@QueryParam("name") String name);

    @GET
    @Path("device/{deviceId}")
    public Uni<Response> getDeviceById(@PathParam("deviceId") String deviceId);

    @GET
    @Path("device/category")
    public Uni<Response> listCategoires(@QueryParam("name") String name);

    @GET
    @Path("device/category/{categoryId}")
    public Uni<Response> getCategoryById(@PathParam("categoryId") String categoryId);

}
