package at.fhv.layblar.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.AddLabeledDataCommand;
import at.fhv.layblar.domain.model.SmartMeterData;
import jakarta.persistence.Entity;

@Entity
public class LabeledDataAddedEvent extends LabeledDataEvent {

        private ObjectMapper mapper = new ObjectMapper();

    public LabeledDataAddedEvent() {
        super();
        this.eventType = "LabeledDataAddedEvent";
    };

    private LabeledDataAddedEvent(AddLabeledDataCommand command) {
        super();
        this.eventType = "LabeledDataAddedEvent";
        this.entityId = UUID.randomUUID().toString();
        this.payload = createEventPayload(command);
    }

    public static LabeledDataAddedEvent create(AddLabeledDataCommand command) {
        return new LabeledDataAddedEvent(command);
    }

    @JsonIgnore
    public String getLabeledDataId() {
        return this.payload.get("labeledDataId").asText();
    }

    @JsonIgnore
    public String getLabelId() {
        return this.payload.get("labelId").asText();
    }

    @JsonIgnore
    public String getHouseholdId() {
        return this.payload.get("householdId").asText();
    }

    @JsonIgnore
    public String getDeviceId() {
        return this.payload.get("deviceId").asText();
    }

    @JsonIgnore
    public String getProjectId() {
        return this.payload.get("projectId").asText();
    }

    @JsonIgnore
    public Boolean getIsTimeEvent() {
        return this.payload.get("isTimeEvent").asBoolean();
    }

    @JsonIgnore
    public LocalDateTime getCreatedAt() {
        return LocalDateTime.parse(this.payload.get("createdAt").asText());
    }

    @JsonIgnore
    public List<SmartMeterData> getSmartMeterData() {
        return mapper.convertValue(payload.get("smartMeterData"),
        new TypeReference<List<SmartMeterData>>() {
        });
    }

    private ObjectNode createEventPayload(AddLabeledDataCommand command) {
        ObjectNode root = mapper.createObjectNode();
        root.put("labeledDataId", entityId);
        root.put("labelId", command.labelId);
        root.put("householdId", command.householdId);
        root.put("deviceId", command.deviceId);
        root.put("projectId", command.projectId);
        root.put("isTimeEvent", command.isTimeEvent);
        root.put("createdAt", LocalDateTime.now().toString());
        root.putPOJO("smartMeterData", command.smartMeterData);
        return root;
    }

    @Override
    public void accept(LabeledDataEventVisitor visitor) {
        visitor.visit(this);
    }
    
}
