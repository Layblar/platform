package at.fhv.layblar;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MeterDataProcessor {

    @Incoming("meter-data")
    @Blocking
    public void saveToDatabase(MeterDataReading meterData){
        MeterDataReading.persist(meterData);
    }
    
}
