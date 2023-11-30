package at.fhv.layblar.rest;

import at.fhv.layblar.application.DeviceService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/device")
public class DeviceResource {

    @Inject
    DeviceService deviceService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDevices(){
        return Response.ok().entity(deviceService.getAllDevices()).build();
    }
}
