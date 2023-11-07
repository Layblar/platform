package at.fhv.layblar.smartMeterServiceRouting;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.print.attribute.standard.Media;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.smartMeterServiceRouting.model.SmartMeterDataDTO;
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
    @APIResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SmartMeterDataDTO.class), examples = @ExampleObject(name = "smart-meter-data", value = "      \"1.7.0\": \"160\",\r\n"
            + //
            "      \"1.8.0\": \"1137778\",\r\n" + //
            "      \"2.7.0\": \"0\",\r\n" + //
            "      \"2.8.0\": \"0\",\r\n" + //
            "      \"3.8.0\": \"3837\",\r\n" + //
            "      \"4.8.0\": \"717727\",\r\n" + //
            "      \"16.7.0\": \"160\",\r\n" + //
            "      \"31.7.0\": \"1.03\",\r\n" + //
            "      \"32.7.0\": \"229.80\",\r\n" + //
            "      \"51.7.0\": \"0.42\",\r\n" + //
            "      \"52.7.0\": \"229.00\",\r\n" + //
            "      \"71.7.0\": \"0.17\",\r\n" + //
            "      \"72.7.0\": \"229.60\",\r\n" + //
            "      \"uptime\": \"0000:01:49:41\",\r\n" + //
            "      \"timestamp\": \"2023-05-06T11:15:40\",\r\n" + //
            "      \"sensorId\": \"0951751081575145\"")), description = "Smart-Meter data for the specified time period", responseCode = "200")
    @Operation(summary = "Get Smart-Meter data", description = "Get a List of Smart-Meter data from the specified time frame")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> getSmartMeterData(
            @Parameter(description = "The id of the household for which the data should be fetched", required = true) @PathParam("householdId") String householdId,
            @Parameter(description = "The begin of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = true) @QueryParam("from") String from,
            @Parameter(description = "The end of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = true) @QueryParam("to") String to) {
        return restClient.getSmartMeterData(householdId, from, to);
    }

}
