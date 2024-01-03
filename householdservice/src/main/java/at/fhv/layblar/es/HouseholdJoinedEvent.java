package at.fhv.layblar.es;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity

public class HouseholdJoinedEvent extends Event {

    public HouseholdJoinedEvent(){
        super();
        this.eventType = "HouseHoldJoinedEvent";
    };

    
    public HouseholdJoinedEvent(Household household){
        super();
        this.eventType = "HouseHoldJoinedEvent";
        this.entityId = household.householdId;
        this.entityType = "Household";
        this.payload = createEventPayload(household);
    }


    @JsonIgnore
    public String getHouseholdId(){
        return this.payload.get("householdId").asText();
    }

    @JsonIgnore
    public String getHouseName(){
        return this.payload.get("householdId").asText();
    }

    private ObjectNode createEventPayload(Household household){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("householdId", household.householdId);
        return root;
    }
    
}
