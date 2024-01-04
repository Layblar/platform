package at.fhv.layblar.events;

public interface DeviceEventVisitor {

    void visit(DeviceAddedEvent event);
    void visit(DeviceUpdatedEvent event);
    void visit(DeviceDeletedEvent event);

}
