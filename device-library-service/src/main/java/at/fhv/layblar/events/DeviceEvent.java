package at.fhv.layblar.events;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "eventType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DeviceAddedEvent"),
        @JsonSubTypes.Type(value = DeviceUpdatedEvent.class, name = "DeviceUpdatedEvent")
})
public abstract class DeviceEvent implements Comparable<DeviceEvent> {

    public String eventId;
    public String eventType;
    public String entityType;
    public String entityId;
    public LocalDateTime timestamp;
    public ObjectNode payload;

    public DeviceEvent(){
        super();
        this.entityType = "Household";
    }

    public abstract String getDeviceId();

    public abstract void accept(EventVisitor visitor);

    @Override
    public int compareTo(DeviceEvent event) {
        return timestamp.compareTo(event.timestamp);
    }

}
