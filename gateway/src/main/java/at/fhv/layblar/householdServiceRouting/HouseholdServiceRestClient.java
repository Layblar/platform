package at.fhv.layblar.householdServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.fhv.layblar.authentication.dto.CreateHouseholdDTO;
import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceDTO;
import at.fhv.layblar.smartMeterServiceRouting.model.SmartMeterDataDTO;
import io.quarkus.rest.client.reactive.NotBody;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "household-service-api")
@RegisterClientHeaders
public interface HouseholdServiceRestClient {

    @POST
    @Path("/household")
    @ClientHeaderParam(name = "Authorization", value = "Bearer {token}")
    Uni<Response> createHousehold(CreateHouseholdDTO createHouseholdDTO, @NotBody String token);

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
