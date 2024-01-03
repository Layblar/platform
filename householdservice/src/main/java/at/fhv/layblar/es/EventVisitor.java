package at.fhv.layblar.es;

public interface EventVisitor {

    void visit(HouseholdCreatedEvent event);

    void visit(HouseholdJoinedEvent event);

    void visit(HouseholdLeftEvent event);

    void visit(SmartMeterRegisteredEvent event);

    void visit(SmartMeterRemovedEvent event);

    void visit(DeviceAddedEvent event);

    void visit(DeviceUpdatedEvent event);

    void visit(DeviceDeletedEvent event);

}
