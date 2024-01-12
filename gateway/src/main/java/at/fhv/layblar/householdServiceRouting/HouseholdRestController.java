package at.fhv.layblar.householdServiceRouting;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.householdServiceRouting.model.AddDeviceDTO;
import at.fhv.layblar.householdServiceRouting.model.HouseholdDTO;
import at.fhv.layblar.householdServiceRouting.model.HouseholdDeviceDTO;
import at.fhv.layblar.householdServiceRouting.model.HouseholdJoinCodeDTO;
import at.fhv.layblar.householdServiceRouting.model.JoinHouseholdDTO;
import at.fhv.layblar.householdServiceRouting.model.LeaveHouseholdDTO;
import at.fhv.layblar.householdServiceRouting.model.SmartMeterDTO;
import at.fhv.layblar.householdServiceRouting.model.UpdateDeviceDTO;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
    HouseholdServiceRestClient restClient;

    @POST
    @Path("/{householdId}/join")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = HouseholdDTO.class)), description = "Your household", responseCode = "200")
    @Operation(summary = "Join household", description = "Join given household")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> joinHousehold(
            @Parameter(description = "The id of the household that should be merged", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "The information of the user that joins the household", required = true) JoinHouseholdDTO joinHouseholdDTO) {
        return restClient.joinHousehold(householdId, joinHouseholdDTO);
    }

    @GET
    @Path("/{householdId}/join")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get household join code", description = "Get the code for joining the given household")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = HouseholdJoinCodeDTO.class)), description = "JoinCode Information", responseCode = "200")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> getHouseholdJoinCode(
            @Parameter(description = "The id of the household for which the join code should be fetched", required = true) @PathParam("householdId") String householdId) {
        return restClient.getJoinHouseholdCode(householdId);
    }

    @POST
    @Path("/{householdId}/leave")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = HouseholdDTO.class)), description = "Your household", responseCode = "200")
    @Operation(summary = "Leave household", description = "Leave given household")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> leaveHousehold(
            @Parameter(description = "The id of the household that should be split", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "The information of the user that leaves the household", required = true) LeaveHouseholdDTO leaveHouseholdDTO) {
        return restClient.leaveHousehold(householdId, leaveHouseholdDTO);
    }

    @GET
    @Path("/{householdId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get household information", description = "Get information about the given household")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = HouseholdDTO.class)), description = "Household with the given id", responseCode = "200")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> getHouseholdInformation(
            @Parameter(description = "The id of the household for which the data should be fetched", required = true) @PathParam("householdId") String householdId) {
        return restClient.getHouseholdInformation(householdId);
    }

    @POST
    @Path("/{householdId}/device")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add device to household", description = "Add device to household")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = HouseholdDTO.class)), description = "The household with the added device", responseCode = "200")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> addDeviceToHousehold(
            @Parameter(description = "The id of the household to which the device should be added", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "The device that should be added to the household", required = true) AddDeviceDTO device) {
        return restClient.addDeviceToHousehold(householdId, device);
    }

    @PUT
    @Path("/{householdId}/device")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update device information", description = "Update device information")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = HouseholdDeviceDTO.class)), description = "The updated device", responseCode = "200")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> updateDeviceInformation(
            @Parameter(description = "The id of the household from which the device should be removed", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "The device object based on the DeviceDTO that should be updated", required = true) UpdateDeviceDTO device) {
        return restClient.updateDeviceInformation(householdId, device);
    }

    @DELETE
    @Path("/{householdId}/device/{deviceId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Remove device from household", description = "Remove device from household")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = HouseholdDeviceDTO.class)), description = "The household without the removed device", responseCode = "200")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> removeDeviceFromHousehold(
            @Parameter(description = "The id of the household from which the device should be removed", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "The id of the device that should be removed from the household", required = true) @PathParam("deviceId") String deviceId) {
        return restClient.removeDeviceFromHousehold(householdId, deviceId);
    }

    @GET
    @Path("/{householdId}/device")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List household devices", description = "List household devices")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = HouseholdDeviceDTO.class)), description = "List of all devices in the household", responseCode = "200")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> listHouseholdDevices(
            @Parameter(description = "The id of the household for which the data should be fetched", required = true) @PathParam("householdId") String householdId) {
        return restClient.listHouseholdDevices(householdId);
    }

    @POST
    @Path("/{householdId}/smartMeterReader")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Register smart meter reader", description = "Register smart meter reader in given household")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = HouseholdDTO.class)), description = "The household with the added smart meter reader", responseCode = "200")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> registerSmartMeterReader(
            @Parameter(description = "The id of the household to which the smart meter reader should be added", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "The smart meter reader that should be added", required = true) SmartMeterDTO smartMeter) {
        return restClient.registerSmartMeterReader(householdId, smartMeter);
    }

    @DELETE
    @Path("/{householdId}/smartMeterReader")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Remove smart meter reader", description = "Remove smart meter reader from given household")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = HouseholdDTO.class)), description = "The household without the removed smart meter reader", responseCode = "200")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> removeSmartMeterReader(
            @Parameter(description = "The id of the household from which the smart meter reader should be removed", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "The smart meter reader that should be removed", required = true) SmartMeterDTO smartMeter) {
        return restClient.removeSmartMeterReader(householdId, smartMeter);
    }

}
