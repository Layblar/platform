package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "eventType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DeviceAddedEvent"),
        @JsonSubTypes.Type(value = DeviceUpdatedEvent.class, name = "DeviceUpdatedEvent"),
        @JsonSubTypes.Type(value = DeviceRemovedEvent.class, name = "DeviceRemovedEvent")
})
public abstract class DeviceEvent extends Event {

    public DeviceEvent(){
        super();
        this.entityType = "Household";
    }

}
