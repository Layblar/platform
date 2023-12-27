package at.fhv.layblar.infrastructure.events;

public interface DeviceEventVisitor {

    void visit(DeviceAddedEvent event);
    void visit(DeviceUpdatedEvent event);
    void visit(DeviceDeletedEvent event);

}
