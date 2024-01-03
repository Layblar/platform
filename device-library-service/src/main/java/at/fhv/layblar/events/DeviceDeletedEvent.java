package at.fhv.layblar.events;

public class DeviceDeletedEvent extends DeviceEvent {

    @Override
    public void accept(DeviceEventVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return this.eventType;
    }


}
