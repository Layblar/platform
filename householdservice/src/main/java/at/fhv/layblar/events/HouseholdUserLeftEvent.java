package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.LeaveHouseholdCommand;
import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class HouseholdUserLeftEvent extends HouseholdEvent {

    public HouseholdUserLeftEvent() {
        super();
        this.eventType = "HouseholdUserLeftEvent";
    };

    private HouseholdUserLeftEvent(LeaveHouseholdCommand command, Household household) {
        super();
        this.eventType = "HouseholdUserLeftEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(command, household);
    }

    public static HouseholdUserLeftEvent create(LeaveHouseholdCommand command, Household household) {
        return new HouseholdUserLeftEvent(command, household);
    }

    @JsonIgnore
    public String getHouseholdId(){
        return payload.get("householdId").asText();
    }

    @JsonIgnore
    public String getUserId(){
        return payload.get("userId").asText();
    }

    private ObjectNode createEventPayload(LeaveHouseholdCommand command, Household household) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("householdId", command.householdId);
        root.put("userId", command.userId);
        return root;
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
