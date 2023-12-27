package at.fhv.layblar.rest;

import org.eclipse.microprofile.reactive.messaging.Channel;
import io.smallrye.reactive.messaging.MutinyEmitter;

import at.fhv.layblar.application.DeviceService;
import at.fhv.layblar.infrastructure.events.DeviceEvent;
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

    @Inject
    @Channel("device-event")
    MutinyEmitter<DeviceEvent> emitter;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDevices(){
        return Response.ok().entity(deviceService.getAllDevices()).build();
    }

    
}
