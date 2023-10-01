package at.fhv.layblar;

import org.eclipse.microprofile.reactive.messaging.Channel;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/data")
public class MeterDataResource {

    @Channel("meter-data") Multi<MeterDataReading> meterData;     

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS) 
    public Multi<MeterDataReading> stream() {
        return meterData; 
    }
}
