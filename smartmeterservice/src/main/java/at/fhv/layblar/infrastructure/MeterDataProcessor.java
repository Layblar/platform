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
        MeterDataReading.persist(mdr);
    }
    
}
