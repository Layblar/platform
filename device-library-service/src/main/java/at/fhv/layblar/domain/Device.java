package at.fhv.layblar.domain;

import java.util.List;
import java.util.Set;

import org.bson.codecs.pojo.annotations.BsonId;

import at.fhv.layblar.events.DeviceAddedEvent;
import at.fhv.layblar.events.DeviceDeletedEvent;
import at.fhv.layblar.events.DeviceUpdatedEvent;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity
public class Device extends PanacheMongoEntityBase {
    
    @BsonId
    public String deviceId;
    public String deviceName;
    public Set<String> alternativeNames;
    public String deviceDescription;
    public String manufacturer;
    public String modelNumber;
    public Integer powerDraw;
    public String energyEfficiencyRating;
    public Float weight;
    public List<DeviceCategory> deviceCategory;

    public Device(){}

    public void createDevice(DeviceAddedEvent event) {
        this.deviceId = event.deviceId;
        this.deviceName = event.deviceName;
        this.deviceDescription = event.deviceDescription;
        this.manufacturer = event.manufacturer;
        this.modelNumber = event.modelNumber;
        this.powerDraw = event.powerDraw;
        this.energyEfficiencyRating = event.energyEfficiencyRating;
        this.weight = event.weight;
        this.deviceCategory = event.deviceCategory;
    }

    public void apply(DeviceAddedEvent event) {
        updateDevice(event.deviceName, event.deviceDescription, event.manufacturer, event.modelNumber,
        event.powerDraw, event.energyEfficiencyRating, event.weight, event.deviceCategory);
    }

    public void apply(DeviceUpdatedEvent event) {
        updateDevice(event.deviceName, event.deviceDescription, event.manufacturer, event.modelNumber,
        event.powerDraw, event.energyEfficiencyRating, event.weight, event.deviceCategory);
    }

    public void apply(DeviceDeletedEvent event) {
    }

    private void updateDevice(String deviceName, String deviceDescription, String manufacturer, String modelNumber, Integer powerDraw, String energyEfficiencyRating, Float weight, List<DeviceCategory> deviceCategory){
        if(deviceName != null) {
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
