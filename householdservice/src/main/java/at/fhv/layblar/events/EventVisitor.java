package at.fhv.layblar.events;

public interface EventVisitor {

    void visit(HouseholdCreatedEvent event);

    void visit(HouseholdUserJoinedEvent event);

    void visit(HouseholdUserLeftEvent event);

    void visit(SmartMeterRegisteredEvent event);

    void visit(SmartMeterRemovedEvent event);

    void visit(DeviceAddedEvent event);

    void visit(DeviceUpdatedEvent event);

    void visit(DeviceRemovedEvent event);

    void visit(HouseholdDeletedEvent event);

}
