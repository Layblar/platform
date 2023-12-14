package at.fhv.layblar.rest;

import at.fhv.layblar.application.LabelSerivce;
import at.fhv.layblar.application.dto.CreateLabelDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/label")
@Produces(MediaType.APPLICATION_JSON)
public class LabelResource {

    @Inject
    LabelSerivce labelService;

    @GET
    @Path("/{householdId}")
    public Response getLabelsByHousehold(@PathParam("householdId") String householdId) {
        return Response.ok().entity(labelService.getLabelsByHouseholdId(householdId)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/label")
    public Response createLabel(CreateLabelDTO createLabelDTO){
        return Response.ok().entity(labelService.createLabel(createLabelDTO)).build();
    }
}
