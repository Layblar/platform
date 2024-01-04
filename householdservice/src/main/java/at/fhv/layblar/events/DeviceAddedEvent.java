package at.fhv.layblar.events;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.application.dto.DeviceCategoryDTO;
import at.fhv.layblar.commands.AddDeviceCommand;
import at.fhv.layblar.domain.DeviceCategory;
import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class DeviceAddedEvent extends DeviceEvent {

    private static final String DEVICE_ID = "deviceId";
    private static final String DEVICE_NAME = "deviceName";
    private static final String DEVICE_DESCRIPTION = "deviceDescription";
    private static final String MANUFACTURER = "manufacturer";
    private static final String MODEL_NUMBER = "modelNumber";
    private static final String POWER_DRAW = "powerDraw";
    private static final String ENERGY_EFFICIENCY_RATING = "energyEfficiencyRating";
    private static final String WEIGHT = "weight";
    private static final String CATEGORIES = "categories";
    private static final String DEVICE_CATEGORY_ID = "deviceCategoryId";
    private static final String DEVICE_CATEGORY_NAME = "deviceCategoryName";
    private static final String DEVICE_CATEGORY_DESCRIPTION = "deviceCategoryDescription";

    public DeviceAddedEvent() {
        super();
        this.eventType = "DeviceAddedEvent";
    };

    public DeviceAddedEvent(AddDeviceCommand command, Household household) {
        super();
        this.eventType = "DeviceAddedEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(command, household);
    }

    @JsonIgnore
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

    private ObjectNode createEventPayload(AddDeviceCommand command, Household household) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        if (command.deviceId == null) {
            command.deviceId = UUID.randomUUID().toString();
        }
        root.put(DEVICE_ID, command.deviceId);
        root.put(DEVICE_NAME, command.deviceName);
        root.put(DEVICE_DESCRIPTION, command.deviceDescription);
        root.put(MANUFACTURER, command.deviceDescription);
        root.put(MODEL_NUMBER, command.deviceDescription);
        root.put(POWER_DRAW, command.deviceDescription);
        root.put(ENERGY_EFFICIENCY_RATING, command.deviceDescription);
        root.put(WEIGHT, command.deviceDescription);
        ArrayNode categories = root.putArray(CATEGORIES);
        for (DeviceCategoryDTO categoryDTO : command.categories) {
            if (categoryDTO.deviceCategoryId == null) {
                categoryDTO.deviceCategoryId = UUID.randomUUID().toString();
            }
            categories.addObject()
                .put(DEVICE_CATEGORY_ID, categoryDTO.deviceCategoryId)
                .put(DEVICE_CATEGORY_NAME, categoryDTO.deviceCategoryName)
                .put(DEVICE_CATEGORY_DESCRIPTION, categoryDTO.deviceCategoryDescription);
        }
        return root;
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

    public static DeviceAddedEvent create(AddDeviceCommand command, Household household) {
        return new DeviceAddedEvent(command, household);
    }

}
