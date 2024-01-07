package at.fhv.layblar.deviceLibraryServiceRouting;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceCategoryDTO;
import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceDTO;
import io.smallrye.mutiny.Uni;

//@Authenticated
@Path("deviceLibrary")
@Tag(name = "Device-Library", description = "Operations about Device Library")
@APIResponse(responseCode = "401", description = "Unauthorized")
@APIResponse(responseCode = "403", description = "Invalid User")
@APIResponse(responseCode = "500", description = "Server Error")
public class DeviceLibraryServiceController {

    @Inject
    @RestClient
    DeviceLibraryServiceRestClient restClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = DeviceDTO.class)), description = "List of devices", responseCode = "200")
    @Operation(summary = "List devices", description = "Get a list of devices from the device library")
    @SecurityRequirement(name = "none")
    public Uni<Response> listDevices(@QueryParam("name") String name) {
        return restClient.listDevices(name);
    }

    @GET
    @Path("/{deviceId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "404", description = "Device not found")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = DeviceDTO.class)), description = "Device", responseCode = "200")
    @Operation(summary = "Device by Id", description = "Get a device by a Id")
    @SecurityRequirement(name = "none")
    public Uni<Response> getDeviceById(@PathParam("deviceId") String deviceId) {
        return restClient.getDeviceById(deviceId);
    }

    @GET
    @Path("/category")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = DeviceCategoryDTO.class)), description = "List of device categories", responseCode = "200")
    @Operation(summary = "List all devices categories", description = "Get a list of device categories from the device library")
    @SecurityRequirement(name = "none")
    public Uni<Response> listCategoires(@QueryParam("name") String name) {
        return restClient.listCategoires(name);
    }

    @GET
    @Path("/category/{categoryId}")
        @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "404", description = "Device category not found")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = DeviceDTO.class)), description = "Device category", responseCode = "200")
    @Operation(summary = "Device category by Id", description = "Get a Device category by a Id")
    @SecurityRequirement(name = "none")   
    public Uni<Response> getCategoryById(@PathParam("categoryId") String categoryId) {
        return restClient.getCategoryById(categoryId);
    }

}
