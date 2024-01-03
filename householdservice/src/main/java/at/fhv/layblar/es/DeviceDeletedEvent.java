package at.fhv.layblar.es;

import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class DeviceDeletedEvent extends DeviceEvent {

        public DeviceDeletedEvent(){
        super();
        this.eventType = "DeviceDeletedEvent";
    };

    
    public DeviceDeletedEvent(Household household){
        super();
        this.eventType = "DeviceDeletedEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(household);
    }

    private ObjectNode createEventPayload(Household household) {
        return null;
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }


}
