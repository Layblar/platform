package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.JoinHouseholdCommand;
import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class HouseholdUserJoinedEvent extends HouseholdEvent {

    public HouseholdUserJoinedEvent() {
        super();
        this.eventType = "HouseholdJoinedEvent";
    };

    public HouseholdUserJoinedEvent(JoinHouseholdCommand command, Household household) {
        super();
        this.eventType = "HouseholdJoinedEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(command, household);
    }

    @JsonIgnore
    public String getHouseholdId() {
        return this.payload.get("householdId").asText();
    }

    @JsonIgnore
    public String getUserId() {
        return this.payload.get("userId").asText();
    }

    @JsonIgnore
    public String getEmail() {
        return this.payload.get("email").asText();
    }

    @JsonIgnore
    public String getFirstName() {
        return this.payload.get("firstName").asText();
    }

    @JsonIgnore
    public String getLastName() {
        return this.payload.get("lastName").asText();
    }

    private ObjectNode createEventPayload(JoinHouseholdCommand command, Household household) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("householdId", household.householdId);
        root.put("userId", command.userId);
        root.put("email", command.email);
        root.put("firstName", command.firstName);
        root.put("lastName", command.lastName);
        return root;
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
