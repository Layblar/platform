package at.fhv.layblar.events;

public interface EventVisitor {

    void visit(DeviceAddedEvent event);

    void visit(DeviceUpdatedEvent event);

}
