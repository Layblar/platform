package at.fhv.layblar.rest;

import java.util.List;

import org.eclipse.microprofile.reactive.messaging.Channel;

import at.fhv.layblar.domain.MeterDataReading;
import at.fhv.layblar.infrastructure.MeterDataRepository;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/data")
public class MeterDataResource {
    
    @Inject
    MeterDataRepository meterDataRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List<MeterDataReading> list() {
        return MeterDataReading.listAll(); 
    }

    // @Channel("meter-data") Multi<MeterDataReading> meterData; 
    // @GET
    // @Path("/steam")
    // @Produces(MediaType.SERVER_SENT_EVENTS) 
    // public Multi<MeterDataReading> stream() {
    //     return meterData; 
    // }

    @GET
    @Path("/bucket")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> bucket() {
        return meterDataRepository.nativeQuery(); 
    }
}
