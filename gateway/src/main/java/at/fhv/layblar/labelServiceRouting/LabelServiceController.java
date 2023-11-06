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

import at.fhv.layblar.labelServiceRouting.model.LabelDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    @Path("/{labelId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "404", description = "No label found")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = LabelDTO.class)), description = "Label by id", responseCode = "200")
    @Operation(summary = "Returns a Label", description = "Returns the Label with the specified id")
    @SecurityRequirement(name = "jwt")
    Uni<Response> getLabel(
            @Parameter(description = "The id of the label for which the data should be fetched", required = true) @PathParam("labelId") String labelId) {
        return restClient.getLabel(labelId);
    }

    @GET
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "400", description = "Missing Query Parameters")
    @APIResponse(responseCode = "422", description = "Wrong Date Format")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = LabelDTO.class)), description = "Labels for the specified time period", responseCode = "200")
    @Operation(summary = "Get Labels", description = "Get a List of Labels from the specified time frame")
    @SecurityRequirement(name = "jwt")
    Uni<Response> getProjectLabels(
            @Parameter(description = "The id of the project for which the data should be fetched", required = true) @PathParam("projectId") String projectId,
            @Parameter(description = "The begin of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = false) @QueryParam("from") String from,
            @Parameter(description = "The end of the time intervall for which the data should be fetched. Needs to be a Unix Timestamp as String", required = false) @QueryParam("to") String to) {
        return restClient.getProjectLabels(projectId, from, to);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "400", description = "Missing Query Parameters")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = LabelDTO.class)), description = "The created label", responseCode = "200")
    @Operation(summary = "Create a Label", description = "Create a Label")
    @SecurityRequirement(name = "jwt")
    Uni<Response> createLabel(
            @Parameter(description = "The label object based on the LabelDTO that should be created", required = true) LabelDTO label) {
        return restClient.createLabel(label);
    }
}
