package at.fhv.layblar.es;

public class SmartMeterRegisteredEvent extends HouseholdEvent {

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
