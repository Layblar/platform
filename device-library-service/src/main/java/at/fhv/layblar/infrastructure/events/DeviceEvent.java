package at.fhv.layblar.infrastructure.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "event_type",
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
    public String event_type;
    public String entity_id;

    public DeviceEvent(){}

    public DeviceEvent(String event_type){
        this.event_type = event_type;
    }

    public abstract String toString();

    public abstract void accept(DeviceEventVisitor v);

}
