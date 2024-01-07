package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.RegisterSmartMeterCommand;
import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class SmartMeterRegisteredEvent extends HouseholdEvent {

    public SmartMeterRegisteredEvent() {
        super();
        this.eventType = "SmartMeterRegisteredEvent";
    };

    private SmartMeterRegisteredEvent(RegisterSmartMeterCommand command, Household household) {
        super();
        this.eventType = "SmartMeterRegisteredEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(command, household);
    }

    public static SmartMeterRegisteredEvent create(RegisterSmartMeterCommand command, Household household){
        return new SmartMeterRegisteredEvent(command, household);
    }

    @JsonIgnore
    public String getSmartMeterId() {
        return payload.get("smartMeterId").asText();
    }

    private ObjectNode createEventPayload(RegisterSmartMeterCommand command, Household household) {
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
