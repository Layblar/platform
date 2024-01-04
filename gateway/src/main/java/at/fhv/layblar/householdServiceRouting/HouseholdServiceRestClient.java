package at.fhv.layblar.householdServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import at.fhv.layblar.householdServiceRouting.model.AddDeviceDTO;
import at.fhv.layblar.householdServiceRouting.model.CreateHouseholdDTO;
import at.fhv.layblar.householdServiceRouting.model.JoinHouseholdDTO;
import at.fhv.layblar.householdServiceRouting.model.LeaveHouseholdDTO;
import at.fhv.layblar.householdServiceRouting.model.SmartMeterDTO;
import at.fhv.layblar.householdServiceRouting.model.UpdateDeviceDTO;
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
    @Path("/household/{householdId}/join")
    Uni<Response> joinHousehold(@PathParam("householdId") String householdId, JoinHouseholdDTO joinHouseholdDTO);

    @GET
    @Path("/household/{householdId}/join")
    Uni<Response> getJoinHouseholdCode(@PathParam("householdId") String householdId);

    @POST
    @Path("/household/{householdId}/leave")
    Uni<Response> leaveHousehold(@PathParam("householdId") String householdId, LeaveHouseholdDTO leaveHouseholdDTO);

    @GET
    @Path("/household/{householdId}")
    Uni<Response> getHouseholdInformation(@PathParam("householdId") String householdId);

    @POST
    @Path("/household/{householdId}/device")
    Uni<Response> addDeviceToHousehold(@PathParam("householdId") String householdId,
            AddDeviceDTO deviceDTO);

    @PUT
    @Path("/household/{householdId}/device")
    Uni<Response> updateDeviceInformation(String householdId, UpdateDeviceDTO device);

    @DELETE
    @Path("/household/{householdId}/device/{deviceId}")
    Uni<Response> removeDeviceFromHousehold(@PathParam("householdId") String householdId,
            @PathParam("deviceId") String deviceId);

    @GET
    @Path("/household/{householdId}/device")
    Uni<Response> listHouseholdDevices(@PathParam("householdId") String householdId);

    @POST
    @Path("household/{householdId}/smartMeterReader")
    Uni<Response> registerSmartMeterReader(@PathParam("householdId") String householdId, SmartMeterDTO smartMeterDTO);

    @DELETE
    @Path("household/{householdId}/smartMeterReader")
    Uni<Response> removeSmartMeterReader(@PathParam("householdId") String householdId, SmartMeterDTO smartMeterDTO);

}
