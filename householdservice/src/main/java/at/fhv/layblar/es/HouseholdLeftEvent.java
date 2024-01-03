package at.fhv.layblar.es;

public class HouseholdLeftEvent extends HouseholdEvent {

    @Override
    public void accept(EventVisitor visitor) {
        visitor.visit(this);
    }

}
