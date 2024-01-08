package at.fhv.layblar.infrastructure.events;

public interface EventVisitor {

    void visit(SmartMeterRegisteredEvent event);

    void visit(SmartMeterRemovedEvent event);

    void visit(HouseholdDeletedEvent event);

}
