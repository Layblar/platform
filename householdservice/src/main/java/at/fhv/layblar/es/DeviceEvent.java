package at.fhv.layblar.es;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "eventType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DeviceAddedEvent"),
        @JsonSubTypes.Type(value = DeviceUpdatedEvent.class, name = "DeviceLockedEvent"),
        @JsonSubTypes.Type(value = DeviceDeletedEvent.class, name = "DeviceUnlockedEvent")
})
public abstract class DeviceEvent extends Event {

    public DeviceEvent(){
        super();
        this.entityType = "Device";
    }

}
