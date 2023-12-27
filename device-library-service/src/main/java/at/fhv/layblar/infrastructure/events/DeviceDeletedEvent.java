package at.fhv.layblar.infrastructure.events;

public class DeviceDeletedEvent extends DeviceEvent {

    @Override
    public void accept(DeviceEventVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return this.event_type;
    }


}
