package at.fhv.layblar.smartMeterServiceRouting;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.authentication.User;
import io.smallrye.mutiny.Uni;

//@Authenticated
@Path("smartmeter")
public class SmartMeterServiceController {

    @Inject
    @RestClient
    SmartMeterServiceRestClient restClient;

    @GET
    @Path("/household/{householdId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getSmartMeterData(@PathParam("household") String householdId, @QueryParam("from") String from, @QueryParam("to") String to) {
        return restClient.getSmartMeterData(householdId, from, to);
    }

    @GET
    @Path("/hello/{id}")
    public String hello(@PathParam("id") String id){
        return id;
    }

    @POST
    public User v2(User id){
        return id;
    }

}
