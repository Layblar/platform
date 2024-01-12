package at.fhv.layblar.application.dto;

import java.util.List;

public class DeviceDTO {

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
