package at.fhv.layblar.events;

import jakarta.persistence.Entity;

@Entity
public class SmartMeterRemovedEvent extends HouseholdEvent {

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
