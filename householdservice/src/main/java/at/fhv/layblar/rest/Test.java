package at.fhv.layblar.rest;

import java.util.List;

import at.fhv.layblar.events.Event;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/test")
public class Test {

    @GET
    public List<Event> test(){
        return Event.listAll();
    }
    
}
