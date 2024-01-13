package at.fhv.layblar.infrastructure.events;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "eventType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HouseholdDeletedEvent.class, name = "HouseholdDeletedEvent")
})
public abstract class HouseholdEvent implements Comparable<HouseholdEvent> {

    public String eventId;
    public String eventType;
    public String entityType;
    public String entityId;
    public LocalDateTime timestamp;
    public ObjectNode payload;

    public HouseholdEvent(){
        super();
        this.entityType = "Household";
    }

    @Override
    public int compareTo(HouseholdEvent event) {
        return timestamp.compareTo(event.timestamp);
    }

}
