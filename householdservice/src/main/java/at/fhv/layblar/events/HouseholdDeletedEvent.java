package at.fhv.layblar.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.LeaveHouseholdCommand;
import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class HouseholdDeletedEvent extends HouseholdEvent {

    public HouseholdDeletedEvent() {
        super();
        this.eventType = "HouseholdDeletedEvent";
    };

    private HouseholdDeletedEvent(LeaveHouseholdCommand command, Household household) {
        super();
        this.eventType = "HouseholdDeletedEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(command, household);
    }

    public static HouseholdDeletedEvent create(LeaveHouseholdCommand command, Household household){
        return new HouseholdDeletedEvent(command, household);
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
