package at.fhv.layblar.infrastructure.events;

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

    static final String DEVICE_ID = "deviceId";
    static final String DEVICE_NAME = "deviceName";
    static final String DEVICE_DESCRIPTION = "deviceDescription";
    static final String MANUFACTURER = "manufacturer";
    static final String MODEL_NUMBER = "modelNumber";
    static final String POWER_DRAW = "powerDraw";
    static final String ENERGY_EFFICIENCY_RATING = "energyEfficiencyRating";
    static final String WEIGHT = "weight";
    static final String CATEGORIES = "categories";
    static final String DEVICE_CATEGORY_ID = "deviceCategoryId";
    static final String DEVICE_CATEGORY_NAME = "deviceCategoryName";
    static final String DEVICE_CATEGORY_DESCRIPTION = "deviceCategoryDescription";

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

    public abstract void accept(DeviceEventVisitor visitor);

    @Override
    public int compareTo(DeviceEvent event) {
        return timestamp.compareTo(event.timestamp);
    }

}
