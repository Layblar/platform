package at.fhv.layblar.rest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import at.fhv.layblar.infrastructure.MeterDataRepository;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Authenticated
@Path("api/data")
public class MeterDataResource {

    @Inject
    MeterDataRepository meterDataRepository;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Path("/household/{householdId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSmartMeterData(
        @PathParam("householdId") String householdId, 
        @QueryParam("from") String from,
        @QueryParam("to") String to,
        @DefaultValue("5 seconds") @QueryParam("interval") String interval) {

        if(!jsonWebToken.containsClaim("householdId") || !householdId.equals(jsonWebToken.getClaim("householdId"))){
            return Response.status(403).entity("Not authorized").build();
        }

        LocalDateTime fromDate = Instant.ofEpochSecond(Long.parseLong(from)).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime toDate = Instant.ofEpochSecond(Long.parseLong(to)).atZone(ZoneId.systemDefault()).toLocalDateTime();

        return Response.ok().entity(meterDataRepository.findByHouseholdId(interval, fromDate, toDate, householdId)
        .stream().map(data -> SmartMeterDataDTO.createSmartMeterDataDTO(data)).collect(Collectors.toList())).build();

    }
}
