package at.fhv.layblar.rest;

import java.util.UUID;

import at.fhv.layblar.domain.Device;
import at.fhv.layblar.domain.SmartMeterData;
import at.fhv.layblar.domain.User;
import at.fhv.layblar.infrastructure.HouseholdRepository;
import at.fhv.layblar.infrastructure.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/data")
public class UserResource {

    @Inject
    HouseholdRepository householdRepository;

    @Inject
    UserRepository userRepository;
    
    @POST
    @Path("/register")
    Response createUser(User user){

        user.id = UUID.randomUUID().toString();
        userRepository.persist(user);

        //TODO: Create Household
        return Response.ok().build();
    }

    @POST
    @Path("/login")
    Response login(User user){

        //TODO: Make this with JWT
        if(userRepository.find("username", user.username).firstResult().password.equals(user.password)){
            return Response.ok().build();
        }else{
            return Response.status(401).build();
        }
    }

    @POST
    @Path("/logout")
    Response logout(){
        //TODO: also with JWT
        return Response.ok().build();

    }


    // I need help jakob :(
    @POST
    @Path("/household/{householdId}/merge")
    Response joinHousehold(@PathParam("householdId") String householdId){
        return Response.ok().build();
    }

    @POST
    @Path("/household/{householdId}/split")
    Response leaveHousehold(@PathParam("householdId") String householdId){
        return Response.ok().build();}

    @GET
    @Path("/household/{householdId}")
    Response getHouseholdInformation(@PathParam("householdId") String householdId){
        return Response.ok().build();
    }

    @POST
    @Path("/household/{householdId}/device/{deviceId}")
    Response addDeviceToHousehold(@PathParam("householdId") String householdId,
            @PathParam("deviceId") String deviceId){
        return Response.ok().build();
    }

    @PUT
    @Path("/household/{householdId}/device")
    Response updateDeviceInformation(String householdId, Device device){
        return Response.ok().build();
    }

    @DELETE
    @Path("/household/{householdId}/device/{deviceId}")
    Response removeDeviceFromHousehold(@PathParam("householdId") String householdId,
            @PathParam("deviceId") String deviceId){
        return Response.ok().build();
    }

    @GET
    @Path("/household/{householdId}/device")
    Response listHouseholdDevices(@PathParam("householdId") String householdId){
        return Response.ok().build();
    }

    @POST
    @Path("household/{householdId}/smartMeterReader")
    Response registerSmartMeterReader(@PathParam("householdId") String householdId, SmartMeterData smartMeter){
        return Response.ok().build();
    }

    @DELETE
    @Path("household/{householdId}/smartMeterReader")
    Response removeSmartMeterReader(@PathParam("householdId") String householdId, SmartMeterData smartMeter){
        return Response.ok().build();
    }
}
