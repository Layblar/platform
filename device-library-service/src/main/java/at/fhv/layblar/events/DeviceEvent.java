package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "eventType",
    visible = true
)
@JsonSubTypes({
    @Type(value = DeviceAddedEvent.class, name = "DeviceAddedEvent"),
    @Type(value = DeviceUpdatedEvent.class, name = "DeviceLockedEvent"),
    @Type(value = DeviceDeletedEvent.class, name = "DeviceUnlockedEvent")
})
public abstract class DeviceEvent {

    public String eventId;
    public Long timestamp;
    public String eventType;
    public String entityId;

    public DeviceEvent(){}

    public DeviceEvent(String eventType){
        this.eventType = eventType;
    }

    public abstract String toString();

    public abstract void accept(DeviceEventVisitor v);

}
