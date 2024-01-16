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

import at.fhv.layblar.labelServiceRouting.model.AddLabeledDataDTO;
import at.fhv.layblar.labelServiceRouting.model.LabelEventDTO;
import at.fhv.layblar.labelServiceRouting.model.LabeledDataDTO;
import at.fhv.layblar.labelServiceRouting.model.UpdateLabeledDataDTO;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Authenticated
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
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = LabeledDataDTO.class)), description = "Labeled data by Household", responseCode = "200")
    @Operation(summary = "Returns a list of labeled data", description = "Returns a list of labeled data for the specific household")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> getLabeledDatasByHousehold(
        @Parameter(description = "The householdId", required = true) @PathParam("householdId") String householdId,
        @Parameter(description = "Optional projectId filter", required = false) @QueryParam("projectId") String projectId) {
        return restClient.getLabeledDatasByHousehold(householdId, projectId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(description = "Label by Household", responseCode = "201")
    @Operation(summary = "Add labeled data", description = "Adds new labeled data to the database")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> addLabeledData(@Parameter(description = "Labeled Data", required = true) AddLabeledDataDTO command){
        return restClient.addLabeledData(command);
    }

    @POST
    @Path("/labelevent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(description = "Label event recived", responseCode = "200")
    @Operation(summary = "Send a label event", description = "Sends a label event to match with smart meter data")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> addLabelEvent(@Parameter(description = "Label event", required = true) LabelEventDTO eventDTO){
        return restClient.addLabelEvent(eventDTO);
    }

    @PUT
    @Path("/{labeledDataId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = LabeledDataDTO.class)), description = "Labeleld data", responseCode = "200")
    @Operation(summary = "Update labeled data", description = "Update the data of a labeled dataset")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> updateLabeledData(
        @Parameter(description = "The id of the labeled dataset to update", required = true) @PathParam("labeledDataId") String labeledDataId,
        @Parameter(description = "Labeled Data", required = true) UpdateLabeledDataDTO command){
        return restClient.updateLabeledData(labeledDataId, command);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{labeledDataId}")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = LabeledDataDTO.class)), description = "Labeleld data", responseCode = "200")
    @Operation(summary = "Delete labeled data", description = "Delete a labeled dataset")
    @SecurityRequirement(name = "jwt")
    public Uni<Response> deleteLabeledData(
        @Parameter(description = "The id of the labeled dataset to delete", required = true) @PathParam("labeledDataId") String labeledDataId){
        return restClient.deleteLabeledData(labeledDataId);
    }
}
