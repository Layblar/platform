package at.fhv.layblar.commands;

import java.util.List;

import at.fhv.layblar.application.dto.DeviceCategoryDTO;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;

public class AddDeviceCommand {
    
    public String deviceId;
    @NotNull
    public String deviceName;
    public String deviceDescription;
    public String manufacturer;
    public String modelNumber;
    public Integer powerDraw;
    public String energyEfficiencyRating;
    public Float weight;
    @NotEmpty
    public List<DeviceCategoryDTO> categories;
    @Override
    
    public String toString() {
        return "AddDeviceCommand [deviceId=" + deviceId + ", deviceName=" + deviceName + ", deviceDescription="
                + deviceDescription + ", manufacturer=" + manufacturer + ", modelNumber=" + modelNumber + ", powerDraw="
                + powerDraw + ", energyEfficiencyRating=" + energyEfficiencyRating + ", weight=" + weight
                + ", categories=" + categories + "]";
    }

    

}
