package at.fhv.matchpoint.apigateway.clubServiceRouting;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.security.Authenticated;

@Authenticated
@Path("club")
public class SmartMeterServiceController {

    // FIXME Implement Interface before using the RestClient
    @Inject
    @RestClient
    ClubServiceRestClient restClient;

}
