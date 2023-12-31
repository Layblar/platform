package at.fhv.layblar.rest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import at.fhv.layblar.domain.MeterDataReading;
import at.fhv.layblar.infrastructure.MeterDataRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/data")
public class MeterDataResource {

    @Inject
    MeterDataRepository meterDataRepository;

    @GET
    @Path("/household/{householdId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSmartMeterData(
        @PathParam("householdId") String householdId, 
        @QueryParam("from") String from,
        @QueryParam("to") String to) {

        LocalDateTime fromDate = Instant.ofEpochSecond(Long.parseLong(from)).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime toDate = Instant.ofEpochSecond(Long.parseLong(to)).atZone(ZoneId.systemDefault()).toLocalDateTime();

        return Response.ok().entity(meterDataRepository.find("time between ?1 and ?2", fromDate, toDate).list()).build();

    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List<MeterDataReading> list() {
        return MeterDataReading.listAll();
    }

    @GET
    @Path("/bucket")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> bucket() {
        return meterDataRepository.nativeQuery();
    }
}
