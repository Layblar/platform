package at.fhv.layblar;

import java.util.List;

import org.eclipse.microprofile.reactive.messaging.Channel;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/data")
public class MeterDataResource {

    //@Channel("meter-data") Multi<MeterDataReading> meterData;     

    @GET
    //@Produces(MediaType.SERVER_SENT_EVENTS) 
    @Produces(MediaType.APPLICATION_JSON)
    public List<MeterDataReading> stream() {
        return MeterDataReading.findAll().list(); 
    }
}
