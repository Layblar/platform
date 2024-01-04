package at.fhv.layblar.rest;

import at.fhv.layblar.application.HouseholdService;
import at.fhv.layblar.commands.AddDeviceCommand;
import at.fhv.layblar.commands.CreateHouseholdCommand;
import at.fhv.layblar.commands.JoinHouseholdCommand;
import at.fhv.layblar.commands.LeaveHouseholdCommand;
import at.fhv.layblar.commands.RegisterSmartMeterCommand;
import at.fhv.layblar.commands.RemoveDeviceCommand;
import at.fhv.layblar.commands.RemoveSmartMeterCommand;
import at.fhv.layblar.commands.UpdateDeviceCommand;
import at.fhv.layblar.utils.exceptions.ResponseException;
import at.fhv.layblar.utils.exceptions.ResponseExceptionBuilder;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
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
    public Response createHousehold(CreateHouseholdCommand command) {
        try {
            return Response.ok().entity(householdService.createHousehold(command)).build();
        } catch (ConstraintViolationException e) {
            return ResponseExceptionBuilder.buildMissingJSONFieldsResponse(e);
        }
    }

    @POST
    @Path("/{householdId}/join")
    public Response joinHousehold(@PathParam("householdId") String householdId, JoinHouseholdCommand command) {
        try {
            return Response.ok().entity(householdService.joinHousehold(householdId, command)).build();
        } catch (ConstraintViolationException e) {
            return ResponseExceptionBuilder.buildMissingJSONFieldsResponse(e);
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @GET
    @Path("/{householdId}/join")
    public Response getJoinHouseholdCode(@PathParam("householdId") String householdId) {
        try {
            return Response.ok().entity(householdService.getHouseholdJoinCodeInformation(householdId)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @POST
    @Path("/{householdId}/leave")
    public Response leaveHousehold(@PathParam("householdId") String householdId, LeaveHouseholdCommand command) {
        try {
            return Response.ok().entity(householdService.leaveHousehold(householdId, command)).build();
        } catch (ConstraintViolationException e) {
            return ResponseExceptionBuilder.buildMissingJSONFieldsResponse(e);
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @GET
    @Path("/{householdId}")
    public Response getHouseholdInformation(@PathParam("householdId") String householdId) {
        try {
            return Response.ok().entity(householdService.getHouseholdInformation(householdId)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @POST
    @Path("/{householdId}/device")
    public Response addDeviceToHousehold(@PathParam("householdId") String householdId,
            AddDeviceCommand command) {
        try {
            return Response.ok().entity(householdService.addDeviceToHousehold(householdId, command)).build();
        } catch (ConstraintViolationException e) {
            return ResponseExceptionBuilder.buildMissingJSONFieldsResponse(e);
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @PUT
    @Path("/{householdId}/device")
    public Response updateDeviceInformation(@PathParam("householdId") String householdId, UpdateDeviceCommand command) {
        try {
            return Response.ok().entity(householdService.updateDeviceInformation(householdId, command)).build();
        } catch (ConstraintViolationException e) {
            return ResponseExceptionBuilder.buildMissingJSONFieldsResponse(e);
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @DELETE
    @Path("/{householdId}/device/{deviceId}")
    public Response removeDeviceFromHousehold(@PathParam("householdId") String householdId,
            @PathParam("deviceId") String deviceId) {
        try {
            return Response.ok().entity(householdService.removeDeviceFromHousehold(householdId, RemoveDeviceCommand.create(deviceId))).build();
        } catch (ConstraintViolationException e) {
            return ResponseExceptionBuilder.buildMissingJSONFieldsResponse(e);
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @GET
    @Path("/{householdId}/device")
    public Response listHouseholdDevices(@PathParam("householdId") String householdId) {
        try {
            return Response.ok().entity(householdService.getHouseholdDevices(householdId)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @POST
    @Path("/{householdId}/smartMeterReader")
    public Response registerSmartMeterReader(@PathParam("householdId") String householdId, RegisterSmartMeterCommand command) {
        try {
            return Response.ok().entity(householdService.registerSmartMeter(householdId, command)).build();
        } catch (ConstraintViolationException e) {
            return ResponseExceptionBuilder.buildMissingJSONFieldsResponse(e);
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @DELETE
    @Path("/{householdId}/smartMeterReader")
    public Response removeSmartMeterReader(@PathParam("householdId") String householdId, RemoveSmartMeterCommand command) {
        try {
            return Response.ok().entity(householdService.removeSmartMeter(householdId, command)).build();
        } catch (ConstraintViolationException e) {
            return ResponseExceptionBuilder.buildMissingJSONFieldsResponse(e);
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }
}
