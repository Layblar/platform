package at.fhv.layblar.es;

public class SmartMeterRemovedEvent extends HouseholdEvent {

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
