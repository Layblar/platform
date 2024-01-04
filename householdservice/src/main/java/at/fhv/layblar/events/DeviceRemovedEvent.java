package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.RemoveDeviceCommand;
import at.fhv.layblar.domain.Household;
import jakarta.persistence.Entity;

@Entity
public class DeviceRemovedEvent extends DeviceEvent {

    public static final String DEVICE_ID = "deviceId";

    public DeviceRemovedEvent(){
        super();
        this.eventType = "DeviceRemovedEvent";
    };

    
    private DeviceRemovedEvent(RemoveDeviceCommand command, Household household){
        super();
        this.eventType = "DeviceRemovedEvent";
        this.entityId = household.householdId;
        this.payload = createEventPayload(command, household);
    }

    @JsonIgnore
    public String getDeviceId(){
        return this.payload.get(DEVICE_ID).asText();
    }

    private ObjectNode createEventPayload(RemoveDeviceCommand command, Household household) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put(DEVICE_ID, command.deviceId);
        return root;
    }

    public static DeviceRemovedEvent create(RemoveDeviceCommand command, Household household){
        return new DeviceRemovedEvent(command, household);
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }


}
