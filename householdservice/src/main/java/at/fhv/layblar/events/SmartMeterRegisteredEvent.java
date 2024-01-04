package at.fhv.layblar.events;

import jakarta.persistence.Entity;

@Entity
public class SmartMeterRegisteredEvent extends HouseholdEvent {

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
