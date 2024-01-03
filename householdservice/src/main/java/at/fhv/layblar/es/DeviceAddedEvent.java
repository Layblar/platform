package at.fhv.layblar.es;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.domain.DeviceCategory;
import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class DeviceAddedEvent extends DeviceEvent {

    public DeviceAddedEvent() {
        super();
        this.eventType = "DeviceAddedEvent";
    };

    public DeviceAddedEvent(Household household) {
        super();
        this.eventType = "DeviceAddedEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(household);
    }

    @JsonIgnore
    public String getDeviceId() {
        return payload.get("deviceId").asText();
    }

    @JsonIgnore
    public String getDeviceName() {
        return payload.get("deviceName").asText();
    }

    @JsonIgnore
    public String getDeviceDescription() {
        return payload.get("deviceDescription").asText();
    }

    @JsonIgnore
    public String getManufacturer() {
        return payload.get("manufacturer").asText();
    }

    @JsonIgnore
    public String getModelNumber() {
        return payload.get("modelNumber").asText();
    }

    @JsonIgnore
    public Integer getPowerDraw() {
        return payload.get("powerDraw").asInt();
    }

    @JsonIgnore
    public String getEnergyEfficiencyRating() {
        return payload.get("energyEfficiencyRating").asText();
    }

    @JsonIgnore
    public Float getWeight() {
        return (float) payload.get("weight").asDouble();
    }

    @JsonIgnore
    public List<DeviceCategory> getDeviceCategory() {
        return new ObjectMapper().convertValue(payload.get("deviceCategory"), new TypeReference<List<DeviceCategory>>() {
        });
    }

    private ObjectNode createEventPayload(Household household) {
        return null;
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
