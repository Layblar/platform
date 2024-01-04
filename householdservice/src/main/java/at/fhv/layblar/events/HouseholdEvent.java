package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "eventType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HouseholdCreatedEvent.class, name = "HouseholdCreatedEvent"),
        @JsonSubTypes.Type(value = HouseholdUserJoinedEvent.class, name = "HouseholdJoinedEvent"),
        @JsonSubTypes.Type(value = SmartMeterRegisteredEvent.class, name = "SmartMeterRegisteredEvent"),
        @JsonSubTypes.Type(value = SmartMeterRemovedEvent.class, name = "SmartMeterRemovedEvent")
})
public abstract class HouseholdEvent extends Event {
    
    public HouseholdEvent(){
        super();
        this.entityType = "Household";
    }
}
