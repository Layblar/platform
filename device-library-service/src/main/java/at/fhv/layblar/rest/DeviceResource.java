package at.fhv.layblar.rest;

import at.fhv.layblar.application.DeviceService;
import at.fhv.layblar.utils.ResponseExceptionBuilder;
import at.fhv.layblar.utils.exceptions.DeviceCategoryNotFoundException;
import at.fhv.layblar.utils.exceptions.DeviceNotFoundException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/device")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource {

    @Inject
    DeviceService deviceService;

    @GET
    public Response listDevices(@QueryParam("name") String name){
        return Response.ok().entity(deviceService.listDevices(name)).build();
    }

    @GET
    @Path("/{deviceId}")
    public Response getDeviceById(@PathParam("deviceId") String deviceId){
        try {
            return Response.ok().entity(deviceService.getDeviceById(deviceId)).build();
        } catch (DeviceNotFoundException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    @GET
    @Path("/category")
    public Response listCategoires(@QueryParam("name") String name){
        return Response.ok().entity(deviceService.listCategories(name)).build();
    }

    @GET
    @Path("/category/{categoryId}")
    public Response getCategoryById(@PathParam("categoryId") String categoryId){
        try {
            return Response.ok().entity(deviceService.getCategoryById(categoryId)).build();
        } catch (DeviceCategoryNotFoundException e) {
            return ResponseExceptionBuilder.buildResponse(e);
        }
    }

    
}
