package at.fhv.layblar.events;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import at.fhv.layblar.application.dto.SmartMeterDataDTO;
import at.fhv.layblar.commands.UpdateLabeledDataCommand;
import at.fhv.layblar.domain.model.Device;
import at.fhv.layblar.domain.model.LabeledData;
import at.fhv.layblar.domain.model.SmartMeterData;
import jakarta.persistence.Entity;

@Entity
public class LabeledDataUpdatedEvent extends LabeledDataEvent {

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    public LabeledDataUpdatedEvent() {
        super();
        this.eventType = "LabeledDataUpdatedEvent";
    };

    private LabeledDataUpdatedEvent(UpdateLabeledDataCommand command, LabeledData labeledData) {
        super();
        this.eventType = "LabeledDataUpdatedEvent";
        this.entityId = labeledData.labeledDataId;
        this.payload = createEventPayload(command);
    }

    public static LabeledDataUpdatedEvent create(UpdateLabeledDataCommand command, LabeledData labeledData) {
        return new LabeledDataUpdatedEvent(command, labeledData);
    }

    @JsonIgnore
    public Device getDevice() {
        return mapper.convertValue(payload.get("device"),
        new TypeReference<Device>() {
        });
    }

    @JsonIgnore
    public List<SmartMeterData> getSmartMeterData() {
        return mapper.convertValue(payload.get("smartMeterData"),
        new TypeReference<List<SmartMeterData>>() {
        });
    }

    private ObjectNode createEventPayload(UpdateLabeledDataCommand command) {
        ObjectNode root = mapper.createObjectNode();
        root.putPOJO("device", command.device);
        ArrayNode smartMeterData = root.putArray("smartMeterData");
        for (SmartMeterDataDTO dataDTO : command.smartMeterData) {
            smartMeterData.addObject()
                .put("time", dataDTO.time.toString())
                .put("sensorId", dataDTO.sensorId)
                .put("1.7.0", dataDTO.v1_7_0)
                .put("1.8.0", dataDTO.v1_8_0)
                .put("2.7.0", dataDTO.v2_7_0)
                .put("2.8.0", dataDTO.v2_8_0)
                .put("3.8.0", dataDTO.v3_8_0)
                .put("4.8.0", dataDTO.v4_8_0)
                .put("16.7.0", dataDTO.v16_7_0)
                .put("31.7.0", dataDTO.v31_7_0)
                .put("32.7.0", dataDTO.v32_7_0)
                .put("51.7.0", dataDTO.v51_7_0)
                .put("52.7.0", dataDTO.v52_7_0)
                .put("71.7.0", dataDTO.v71_7_0)
                .put("72.7.0", dataDTO.v72_7_0);
        }
        return root;
    }

    @Override
    public void accept(LabeledDataEventVisitor visitor) {
        visitor.visit(this);
    }
    
}
