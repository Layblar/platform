package at.fhv.layblar.infrastructure.events;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import at.fhv.layblar.domain.DeviceCategory;

public class DeviceAddedEvent extends DeviceEvent {

    public DeviceAddedEvent() {
        super();
        this.eventType = "DeviceAddedEvent";
    };

    @JsonIgnore
    @Override
    public String getDeviceId() {
        return payload.get(DEVICE_ID).asText();
    }

    @JsonIgnore
    public String getDeviceName() {
        return payload.get(DEVICE_NAME).asText();
    }

    @JsonIgnore
    public String getDeviceDescription() {
        return payload.get(DEVICE_DESCRIPTION).asText();
    }

    @JsonIgnore
    public String getManufacturer() {
        return payload.get(MANUFACTURER).asText();
    }

    @JsonIgnore
    public String getModelNumber() {
        return payload.get(MODEL_NUMBER).asText();
    }

    @JsonIgnore
    public Integer getPowerDraw() {
        return payload.get(POWER_DRAW).asInt();
    }

    @JsonIgnore
    public String getEnergyEfficiencyRating() {
        return payload.get(ENERGY_EFFICIENCY_RATING).asText();
    }

    @JsonIgnore
    public Float getWeight() {
        return (float) payload.get(WEIGHT).asDouble();
    }

    @JsonIgnore
    public List<DeviceCategory> getDeviceCategory() {
        return new ObjectMapper().convertValue(payload.get(CATEGORIES),
                new TypeReference<List<DeviceCategory>>() {
                });
    }

    @Override
    public void accept(DeviceEventVisitor visitor) {
        visitor.visit(this);
    }

}
