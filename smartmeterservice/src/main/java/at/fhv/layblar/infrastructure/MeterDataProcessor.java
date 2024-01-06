package at.fhv.layblar.infrastructure;

import java.time.LocalDateTime;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.fhv.layblar.domain.MeterDataReading;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MeterDataProcessor {

    final String ID_1 = "TESTMETER-1";
    final String ID_2 = "TESTMETER-2";
    
    ObjectMapper mapper = new ObjectMapper();

    @Incoming("meter-data")
    @Blocking    
    public void processData(JsonObject data) throws JsonMappingException, JsonProcessingException {
       saveToDatabase(data);
    }

    @Transactional
    public void saveToDatabase(JsonObject data) throws JsonMappingException, JsonProcessingException{
        MeterDataReading mdr = mapper.readValue(data.encode(),MeterDataReading.class);
        mdr.time = LocalDateTime.parse(data.getString("timestamp"));
        mdr.sensorId = ID_1;
        mdr.householdId = "1";
        MeterDataReading.persist(mdr);
        MeterDataReading mdr2 = mapper.readValue(data.encode(),MeterDataReading.class);
        mdr2.time = LocalDateTime.parse(data.getString("timestamp"));
        mdr2.sensorId = ID_2;
        mdr2.householdId = "2";
        MeterDataReading.persist(mdr2);
    }
    
}
