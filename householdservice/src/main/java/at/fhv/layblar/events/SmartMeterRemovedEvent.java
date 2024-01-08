package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.RemoveSmartMeterCommand;
import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class SmartMeterRemovedEvent extends HouseholdEvent {

    public SmartMeterRemovedEvent() {
        super();
        this.eventType = "SmartMeterRemovedEvent";
    };

    private SmartMeterRemovedEvent(RemoveSmartMeterCommand command, Household household) {
        super();
        this.eventType = "SmartMeterRemovedEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(command, household);
    }

    public static SmartMeterRemovedEvent create(RemoveSmartMeterCommand command, Household household){
        return new SmartMeterRemovedEvent(command, household);
    }

    @JsonIgnore
    public String getSmartMeterId() {
        return payload.get("smartMeterId").asText();
    }

    private ObjectNode createEventPayload(RemoveSmartMeterCommand command, Household household) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("householdId", household.householdId);
        root.put("smartMeterId", command.smartMeterId);
        return root;
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
