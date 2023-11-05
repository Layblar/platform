package at.fhv.layblar.userServiceRouting;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceDTO;
import at.fhv.layblar.smartMeterServiceRouting.model.SmartMeterDataDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

//@Authenticated
@Path("household")
@Tag(name = "Household", description = "Household Operations")
@APIResponse(responseCode = "401", description = "Unauthorized")
@APIResponse(responseCode = "403", description = "Invalid User")
@APIResponse(responseCode = "500", description = "Server Error")
public class HouseholdRestController {

    @Inject
    @RestClient
    UserServiceRestClient restClient;

    @POST
    @Path("/{householdId}/merge")
    Uni<Response> joinHousehold(@PathParam("householdId") String householdId){
        return restClient.joinHousehold(householdId);
    }

    @POST
    @Path("/{householdId}/split")
    Uni<Response> leaveHousehold(@PathParam("householdId") String householdId){
        return restClient.leaveHousehold(householdId);
    }

    @GET
    @Path("/{householdId}")
    Uni<Response> getHouseholdInformation(@PathParam("householdId") String householdId){
        return restClient.getHouseholdInformation(householdId);
    }

    @POST
    @Path("/{householdId}/device")
    Uni<Response> addDeviceToHousehold(@PathParam("householdId") String householdId, @PathParam("deviceId") String deviceId){
        return restClient.addDeviceToHousehold(householdId, deviceId);
    }

    @PUT
    @Path("/{householdId}/device")
    Uni<Response> updateDeviceInformation(DeviceDTO device){
        return restClient.updateDeviceInformation(device);
    }

    @DELETE
    @Path("/{householdId}/device/{deviceId}")
    Uni<Response> removeDeviceFromHousehold(@PathParam("householdId") String householdId, @PathParam("deviceId") String deviceId){
        return restClient.removeDeviceFromHousehold(householdId, deviceId);}

    @GET
    @Path("/{householdId}/device")
    Uni<Response> listHouseholdDevices(@PathParam("householdId") String householdId){
        return restClient.listHouseholdDevices(householdId);
    }

    @POST
    @Path("/{householdId}/smartMeterReader")
    Uni<Response> registerSmartMeterReader(SmartMeterDataDTO smartMeter){
        return restClient.registerSmartMeterReader(smartMeter);
    }

    @DELETE
    @Path("/{householdId}/smartMeterReader")
    Uni<Response> removeSmartMeterReader(SmartMeterDataDTO smartMeter){
        return restClient.removeSmartMeterReader(smartMeter);
    }
    
}
