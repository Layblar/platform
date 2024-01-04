package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class HouseholdUserJoinedEvent extends HouseholdEvent {

    public HouseholdUserJoinedEvent() {
        super();
        this.eventType = "HouseholdJoinedEvent";
    };

    public HouseholdUserJoinedEvent(Household household) {
        super();
        this.eventType = "HouseholdJoinedEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(household);
    }

    @JsonIgnore
    public String getHouseholdId() {
        return this.payload.get("householdId").asText();
    }

    private ObjectNode createEventPayload(Household household) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("householdId", household.householdId);
        return root;
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
