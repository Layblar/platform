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
    @APIResponse(responseCode = "404", description = "No devices found")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = DeviceDTO.class)), description = "List of all devices", responseCode = "200")
    @Operation(summary = "List all devices", description = "Get the complete list of all devices from the device library")
    @SecurityRequirement(name = "none")
    public Uni<Response> listDevices() {
        return restClient.listDevices();
    }

}
