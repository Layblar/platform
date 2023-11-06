package at.fhv.layblar.userServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceDTO;
import at.fhv.layblar.smartMeterServiceRouting.model.SmartMeterDataDTO;
import at.fhv.layblar.userServiceRouting.model.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "user-service-api")
@RegisterClientHeaders
public interface UserServiceRestClient {

    @POST
    @Path("/register")
    Uni<Response> createUser(UserDTO user);

    @POST
    @Path("/login")
    Uni<Response> login(UserDTO user);

    @POST
    @Path("/logout")
    Uni<Response> logout();

    @POST
    @Path("/household/{householdId}/merge")
    Uni<Response> joinHousehold(@PathParam("householdId") String householdId);

    @POST
    @Path("/household/{householdId}/split")
    Uni<Response> leaveHousehold(@PathParam("householdId") String householdId);

    @GET
    @Path("/household/{householdId}")
    Uni<Response> getHouseholdInformation(@PathParam("householdId") String householdId);

    @POST
    @Path("/household/{householdId}/device/{deviceId}")
    Uni<Response> addDeviceToHousehold(@PathParam("householdId") String householdId,
            @PathParam("deviceId") String deviceId);

    @PUT
    @Path("/household/{householdId}/device")
    Uni<Response> updateDeviceInformation(String householdId, DeviceDTO device);

    @DELETE
    @Path("/household/{householdId}/device/{deviceId}")
    Uni<Response> removeDeviceFromHousehold(@PathParam("householdId") String householdId,
            @PathParam("deviceId") String deviceId);

    @GET
    @Path("/household/{householdId}/device")
    Uni<Response> listHouseholdDevices(@PathParam("householdId") String householdId);

    @POST
    @Path("household/{householdId}/smartMeterReader")
    Uni<Response> registerSmartMeterReader(@PathParam("householdId") String householdId, SmartMeterDataDTO smartMeter);

    @DELETE
    @Path("household/{householdId}/smartMeterReader")
    Uni<Response> removeSmartMeterReader(@PathParam("householdId") String householdId, SmartMeterDataDTO smartMeter);

}
