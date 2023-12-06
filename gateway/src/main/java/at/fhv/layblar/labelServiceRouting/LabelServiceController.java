package at.fhv.layblar.labelServiceRouting;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.labelServiceRouting.model.CreateLabelDTO;
import at.fhv.layblar.labelServiceRouting.model.LabeledDataDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("label")
@Tag(name = "Label", description = "Operations for labeling")
@APIResponse(responseCode = "401", description = "Unauthorized")
@APIResponse(responseCode = "403", description = "Invalid User")
@APIResponse(responseCode = "500", description = "Server Error")
public class LabelServiceController {

    @Inject
    @RestClient
    LabelServiceRestClient restClient;

    @GET
    @Path("/{householdId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = LabeledDataDTO.class)), description = "Label by Household", responseCode = "200")
    @Operation(summary = "Returns a list of labels", description = "Returns a list of labels for the specific household")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> getLabelsByHousehold(
            @Parameter(description = "The id of the household for which the data should be fetched", required = true) @PathParam("householdId") String householdId) {
        return restClient.getLabelsByHousehold(householdId);
    }

    // @GET
    // @Path("/project/{projectId}")
    // @Produces(MediaType.APPLICATION_JSON)
    // @APIResponse(responseCode = "400", description = "Missing Query Parameters")
    // @APIResponse(responseCode = "422", description = "Wrong Date Format")
    // @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = LabelDTO.class)), description = "Labels for the specified time period", responseCode = "200")
    // @Operation(summary = "Get Labels", description = "Get a List of Labels from the specified time frame")
    // @SecurityRequirement(name = "jwt")
    // public Uni<Response> getProjectLabels(
    //         @Parameter(description = "The id of the project for which the data should be fetched", required = true) @PathParam("projectId") String projectId,
    //         @Parameter(description = "The begin of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = false) @QueryParam("from") String from,
    //         @Parameter(description = "The end of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = false) @QueryParam("to") String to) {
    //     return restClient.getProjectLabels(projectId, from, to);
    // }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "400", description = "Missing Query Parameters")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = CreateLabelDTO.class)), description = "The created label", responseCode = "200")
    @Operation(summary = "Create a Label", description = "Create a Label")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> createLabel(
            @Parameter(description = "The label object based on the LabelDTO that should be created", required = true) CreateLabelDTO createLabelDTO) {
        return restClient.createLabel(createLabelDTO);
    }
}
