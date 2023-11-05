package at.fhv.matchpoint.apigateway.clubServiceRouting;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "club-service-api")
@RegisterClientHeaders
public interface ClubServiceRestClient {
    
}
