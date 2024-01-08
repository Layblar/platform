package at.fhv.layblar.infrastructure.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SmartMeterRemovedEvent extends HouseholdEvent {

    public SmartMeterRemovedEvent() {
        super();
        this.eventType = "SmartMeterRemovedEvent";
    };

    @JsonIgnore
    public String getSmartMeterId() {
        return payload.get("smartMeterId").asText();
    }

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
