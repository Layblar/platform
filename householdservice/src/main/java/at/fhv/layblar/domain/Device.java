package at.fhv.layblar.domain;

import java.util.List;

import at.fhv.layblar.events.DeviceAddedEvent;
import at.fhv.layblar.events.DeviceUpdatedEvent;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Device extends PanacheEntityBase {

    @Id
    public String deviceId;
    public String deviceName;
    public String deviceDescription;
    public String manufacturer;
    public String modelNumber;
    public Integer powerDraw;
    public String energyEfficiencyRating;
    public Float weight;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<DeviceCategory> categories;

    public Device(){}

    

    private Device(String deviceId, String deviceName, String deviceDescription, String manufacturer, String modelNumber,
            Integer powerDraw, String energyEfficiencyRating, Float weight, List<DeviceCategory> categories) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDescription = deviceDescription;
        this.manufacturer = manufacturer;
        this.modelNumber = modelNumber;
        this.powerDraw = powerDraw;
        this.energyEfficiencyRating = energyEfficiencyRating;
        this.weight = weight;
        this.categories = categories;
    }



    public static Device create(DeviceAddedEvent event) {
        return new Device(event.getDeviceId(), event.getDeviceName(), event.getDeviceDescription(), event.getManufacturer(), event.getModelNumber(), event.getPowerDraw(), event.getEnergyEfficiencyRating(), event.getWeight(), event.getDeviceCategory());
    }



    public void updateDeviceInformation(DeviceUpdatedEvent event) {
        this.deviceId = event.getDeviceId();
        this.deviceName = event.getDeviceName();
        this.deviceDescription = event.getDeviceDescription();
        this.manufacturer = event.getManufacturer();
        this.modelNumber = event.getModelNumber();
        this.powerDraw = event.getPowerDraw();
        this.energyEfficiencyRating = event.getEnergyEfficiencyRating();
        this.weight = event.getWeight();        
        for (DeviceCategory deviceCategory : event.getDeviceCategory()) {
            this.categories.remove(deviceCategory);
            this.categories.add(deviceCategory);
        }
    }



}
