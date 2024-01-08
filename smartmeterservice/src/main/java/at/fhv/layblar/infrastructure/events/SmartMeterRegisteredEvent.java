package at.fhv.layblar.infrastructure.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SmartMeterRegisteredEvent extends HouseholdEvent {

    public SmartMeterRegisteredEvent() {
        super();
        this.eventType = "SmartMeterRegisteredEvent";
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
