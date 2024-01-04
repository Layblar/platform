package at.fhv.layblar.commands;

import java.util.List;

import at.fhv.layblar.application.dto.DeviceCategoryDTO;
import io.smallrye.common.constraint.NotNull;

public class UpdateDeviceCommand {
    
    @NotNull
    public String deviceId;
    public String deviceName;
    public String deviceDescription;
    public String manufacturer;
    public String modelNumber;
    public Integer powerDraw;
    public String energyEfficiencyRating;
    public Float weight;
    public List<DeviceCategoryDTO> categories;

}
