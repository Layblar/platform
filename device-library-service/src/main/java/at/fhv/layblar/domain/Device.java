package at.fhv.layblar.domain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bson.codecs.pojo.annotations.BsonId;

import at.fhv.layblar.events.DeviceAddedEvent;
import at.fhv.layblar.events.DeviceUpdatedEvent;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity
public class Device extends PanacheMongoEntityBase {
    
    @BsonId
    public String deviceId;
    public String deviceName;
    public Set<String> alternativeNames = new HashSet<>();
    public String deviceDescription;
    public String manufacturer;
    public String modelNumber;
    public Integer powerDraw;
    public String energyEfficiencyRating;
    public Float weight;
    public List<DeviceCategory> deviceCategory = new LinkedList<>();

    public Device(){}

    public void apply(DeviceAddedEvent event) {
        this.deviceId = event.getDeviceId();
        updateDevice(event.getDeviceName(), event.getDeviceDescription(), event.getManufacturer(), event.getModelNumber(),
        event.getPowerDraw(), event.getEnergyEfficiencyRating(), event.getWeight(), event.getDeviceCategory());
    }

    public void apply(DeviceUpdatedEvent event) {
        updateDevice(event.getDeviceName(), event.getDeviceDescription(), event.getManufacturer(), event.getModelNumber(),
        event.getPowerDraw(), event.getEnergyEfficiencyRating(), event.getWeight(), event.getDeviceCategory());
    }

    private void updateDevice(String deviceName, String deviceDescription, String manufacturer, String modelNumber, Integer powerDraw, String energyEfficiencyRating, Float weight, List<DeviceCategory> deviceCategory){
        if(deviceName != null && this.deviceName == null){
            this.deviceName = deviceName;
        }        
        if(deviceName != null && !this.deviceName.equals(deviceName)){
            this.alternativeNames.add(deviceName);
        }
        if(manufacturer != null) {
            this.manufacturer = manufacturer;
        }
        if(modelNumber != null) {
            this.modelNumber = modelNumber;
        }
        if(powerDraw != null) {
            this.powerDraw = powerDraw;
        }
        if(energyEfficiencyRating != null){
            this.energyEfficiencyRating = energyEfficiencyRating;
        }
        if(weight != null) {
            this.weight = weight;
        }
        deviceCategory.forEach(category -> {
            this.deviceCategory.removeIf(existingCategory -> existingCategory.equals(category));
            this.deviceCategory.add(category);
        });
    }
}
