package at.fhv.layblar.rest;

import java.util.List;

import at.fhv.layblar.application.LabeledDataService;
import at.fhv.layblar.application.dto.LabelEventDTO;
import at.fhv.layblar.commands.AddLabeledDataCommand;
import at.fhv.layblar.commands.UpdateLabeledDataCommand;
import at.fhv.layblar.domain.model.Project;
import at.fhv.layblar.utils.ResponseExceptionBuilder;
import at.fhv.layblar.utils.exceptions.ResponseException;
import io.quarkus.security.Authenticated;
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
@Path("/api/label")
@Produces(MediaType.APPLICATION_JSON)
public class LabelResource {

    @Inject
    LabeledDataService labledDataService;

    @GET
    public List<Project> test(){
        return Project.findByParticipant("7acd5633-2d83-48ce-821f-41fccbbb2377");
    }

    @GET
    @Path("/{householdId}")
    public Response getLabeledDatasByHousehold(@PathParam("householdId") String householdId, @QueryParam("projectId") String projectId) {
        try {
            return Response.ok().entity(labledDataService.getLabeledDataByHousehold(householdId, projectId)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLabeledData(AddLabeledDataCommand command){
        try {
            return Response.status(201).entity(labledDataService.addLabeledData(command)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @POST
    @Path("/event")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLabelEvent(LabelEventDTO eventDTO){
        try {
            labledDataService.sendLabelEvent(eventDTO);
            return Response.ok().build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{labeledDataId}")
    public Response updateLabeledData(@PathParam("labeledDataId") String labeledDataId, UpdateLabeledDataCommand command){
        try {
            return Response.ok().entity(labledDataService.updateLabeledData(command)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{labeledDataId}")
    public Response deleteLabeledData(@PathParam("labeledDataId") String labeledDataId){
        try {
            return Response.ok().entity(labledDataService.deleteLabeledData(labeledDataId)).build();
        } catch (ResponseException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }
}
