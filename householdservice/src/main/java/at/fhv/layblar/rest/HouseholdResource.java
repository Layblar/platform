package at.fhv.layblar.rest;

import at.fhv.layblar.application.CreateHouseholdDTO;
import at.fhv.layblar.application.HouseholdService;
import at.fhv.layblar.domain.Device;
import at.fhv.layblar.domain.SmartMeter;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Authenticated
@Path("/api/household")
public class HouseholdResource {

    @Inject
    HouseholdService householdService;

    @POST
    public Response createHousehold(CreateHouseholdDTO createHouseholdDTO){
        return Response.ok().entity(householdService.createHousehold(createHouseholdDTO)).build();
    }
    
    @POST
    @Path("/{householdId}/merge")
    Response joinHousehold(@PathParam("householdId") String householdId){
        return Response.ok().build();
    }

    @POST
    @Path("/{householdId}/split")
    Response leaveHousehold(@PathParam("householdId") String householdId){
        return Response.ok().build();}

    @GET
    @Path("/{householdId}")
    Response getHouseholdInformation(@PathParam("householdId") String householdId){
        return Response.ok().build();
    }

    @POST
    @Path("/{householdId}/device/{deviceId}")
    Response addDeviceToHousehold(@PathParam("householdId") String householdId,
            @PathParam("deviceId") String deviceId){
        return Response.ok().build();
    }

    @PUT
    @Path("/{householdId}/device")
    Response updateDeviceInformation(String householdId, Device device){
        return Response.ok().build();
    }

    @DELETE
    @Path("/{householdId}/device/{deviceId}")
    Response removeDeviceFromHousehold(@PathParam("householdId") String householdId,
            @PathParam("deviceId") String deviceId){
        return Response.ok().build();
    }

    @GET
    @Path("/{householdId}/device")
    Response listHouseholdDevices(@PathParam("householdId") String householdId){
        return Response.ok().build();
    }

    @POST
    @Path("/{householdId}/smartMeterReader")
    Response registerSmartMeterReader(@PathParam("householdId") String householdId, SmartMeter smartMeter){
        return Response.ok().build();
    }

    @DELETE
    @Path("/{householdId}/smartMeterReader")
    Response removeSmartMeterReader(@PathParam("householdId") String householdId, SmartMeter smartMeter){
        return Response.ok().build();
    }
}
