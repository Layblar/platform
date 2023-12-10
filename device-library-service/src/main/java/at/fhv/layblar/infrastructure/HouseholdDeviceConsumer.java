package at.fhv.layblar.infrastructure;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HouseholdDeviceConsumer {

    @Incoming("household")
    public void consume(HouseholdEvent householdEvent){
        
    }

    
}
