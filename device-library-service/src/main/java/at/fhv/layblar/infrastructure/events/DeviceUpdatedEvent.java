package at.fhv.layblar.infrastructure.events;

import java.util.List;

import at.fhv.layblar.domain.DeviceCategory;

public class DeviceUpdatedEvent extends DeviceEvent {

    public String deviceId;
    public String deviceName;
    public String deviceDescription;
    public String manufacturer;
    public String modelNumber;
    public Integer powerDraw;
    public String energyEfficiencyRating;
    public Float weight;
    public List<DeviceCategory> deviceCategory;

    @Override
    public void accept(DeviceEventVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return this.event_type;
    }


}
