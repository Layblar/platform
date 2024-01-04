package at.fhv.layblar.events;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.CreateHouseholdCommand;
import jakarta.persistence.Entity;

@Entity
public class HouseholdCreatedEvent extends HouseholdEvent {

    public HouseholdCreatedEvent() {
        super();
        this.eventType = "HouseholdCreatedEvent";
    };

    private HouseholdCreatedEvent(CreateHouseholdCommand command) {
        super();
        this.eventType = "HouseholdCreatedEvent";
        this.entityId = UUID.randomUUID().toString();
        this.payload = createEventPayload(command);
    }

    public static HouseholdCreatedEvent create(CreateHouseholdCommand command) {
        return new HouseholdCreatedEvent(command);
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

    private ObjectNode createEventPayload(CreateHouseholdCommand command) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
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
