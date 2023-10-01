package at.fhv.layblar;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Generator {

    @Outgoing("meter-data")
    public Multi<MeterDataReading> generateMeterData() { 
        AtomicInteger count = new AtomicInteger();
        // create a new Datapoint every 5 seconds and send it to the queue
        return Multi.createFrom().ticks().every(Duration.ofMillis(5000))
                .map(l -> new MeterDataReading(String.valueOf(count.incrementAndGet()), "TestData"))
                .onOverflow().drop();
    }
}
