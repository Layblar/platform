package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.RemoveLabeledDataCommand;
import at.fhv.layblar.domain.model.LabeledData;
import jakarta.persistence.Entity;

@Entity
public class LabeledDataRemovedEvent extends LabeledDataEvent {

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    public LabeledDataRemovedEvent() {
        super();
        this.eventType = "LabeledDataRemovedEvent";
    };

    private LabeledDataRemovedEvent(RemoveLabeledDataCommand command, LabeledData labeledData) {
        super();
        this.eventType = "LabeledDataRemovedEvent";
        this.entityId = labeledData.labeledDataId;
        this.payload = createEventPayload(command);
    }

    public static LabeledDataRemovedEvent create(RemoveLabeledDataCommand command, LabeledData labeledData) {
        return new LabeledDataRemovedEvent(command, labeledData);
    }

    @JsonIgnore
    public String getLabeledDataId() {
        return this.payload.get("labeledDataId").asText();
    }

    private ObjectNode createEventPayload(RemoveLabeledDataCommand command) {
        ObjectNode root = mapper.createObjectNode();
        root.put("labeledDataId", command.labeledDataId);
        return root;
    }

    @Override
    public void accept(LabeledDataEventVisitor visitor) {
        visitor.visit(this);
    }
    
}
