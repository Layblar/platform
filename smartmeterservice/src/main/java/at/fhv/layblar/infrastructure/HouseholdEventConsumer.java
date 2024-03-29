package at.fhv.layblar.infrastructure;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.domain.Household;
import at.fhv.layblar.domain.MeterDataReading;
import at.fhv.layblar.infrastructure.events.EventVisitor;
import at.fhv.layblar.infrastructure.events.HouseholdDeletedEvent;
import at.fhv.layblar.infrastructure.events.HouseholdEvent;
import at.fhv.layblar.infrastructure.events.SmartMeterRegisteredEvent;
import at.fhv.layblar.infrastructure.events.SmartMeterRemovedEvent;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class HouseholdEventConsumer {

    @Inject
    ObjectMapper mapper;

    @Inject
    MeterDataProcessor processor;

    @Incoming("household")
    @Blocking
    @Transactional
    public void process(Record<String, JsonNode> record) {
        try {
            HouseholdEvent event = deserializeEvent(record.value());
            Optional<Household> optHousehold = Household.findByIdOptional(event.entityId);
            Household household = new Household();
            if (optHousehold.isPresent()) {
                household = optHousehold.get();
            }
            household.householdId = event.entityId;
            household = handleHouseholdEvent(household, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Household handleHouseholdEvent(Household household, HouseholdEvent event) {
        event.accept(new EventVisitor() {

            @Override
            public void visit(SmartMeterRegisteredEvent event) {
                household.smartMeterId.add(event.getSmartMeterId());
                processor.createChannelAsync(household.householdId, event.getSmartMeterId());
                household.persist();

            }

            @Override
            public void visit(SmartMeterRemovedEvent event) {
                household.smartMeterId.remove(event.getSmartMeterId());
                processor.deleteQueue(event.getSmartMeterId());
                household.persist();
            }

            @Override
            public void visit(HouseholdDeletedEvent event) {
                for (String smartMeterId : household.smartMeterId) {
                    processor.deleteQueue(smartMeterId);
                }
                MeterDataReading.delete("householdId", household.householdId);
                Household.deleteById(household.householdId);
            }

        });
        return household;
    }

    private HouseholdEvent deserializeEvent(JsonNode value) throws JsonMappingException, JsonProcessingException {
        ObjectNode root = mapper.createObjectNode();
        root.put("entityId", value.get("after").get("entityid").asText());
        root.put("entityType", value.get("after").get("entitytype").asText());
        root.put("eventType", value.get("after").get("eventtype").asText());
        root.put("eventId", value.get("after").get("eventid").asText());
        // Timestamp is sent as microseconds so it must be divided by 1000 to get
        // milliseconds
        Instant instant = Instant.ofEpochMilli(value.get("after").get("timestamp").asLong() / 1000);
        LocalDateTime timestamp = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        root.put("timestamp", timestamp.toString());
        JsonNode payload = mapper.readTree(value.get("after").get("payload").asText());
        root.set("payload", payload);
        return mapper.treeToValue(root, HouseholdEvent.class);
    }

}
