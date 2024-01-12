package at.fhv.layblar.smartMeterServiceRouting;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.smartMeterServiceRouting.model.SmartMeterDataDTO;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;

//@Authenticated
@Path("smartmeter")
@Tag(name = "Smart-Meter", description = "Operations about Smart-Meter data")
@APIResponse(responseCode = "401", description = "Unauthorized")
@APIResponse(responseCode = "403", description = "Invalid User")
@APIResponse(responseCode = "500", description = "Server Error")
public class SmartMeterServiceController {

    @Inject
    @RestClient
    SmartMeterServiceRestClient restClient;

    @GET
    @Path("/household/{householdId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "400", description = "Missing Query Parameters")
    @APIResponse(responseCode = "422", description = "Wrong Date Format")
    @APIResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SmartMeterDataDTO.class)), description = "Smart-Meter data for the specified time period", responseCode = "200")
    @Operation(summary = "Get Smart-Meter data", description = "Get a List of Smart-Meter data from the specified time frame")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> getSmartMeterData(
            @Parameter(description = "The id of the household for which the data should be fetched", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "The begin of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = true) @QueryParam("from") String from,
            @Parameter(description = "The end of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = true) @QueryParam("to") String to) {
        return restClient.getSmartMeterData(householdId, from, to);
    }

}
