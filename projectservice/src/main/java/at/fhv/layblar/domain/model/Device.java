package at.fhv.layblar.domain.model;

import java.util.List;

public class Device {
    
    public String deviceId;
    public String deviceName;
    public String deviceDescription;
    public String manufacturer;
    public String modelNumber;
    public Integer powerDraw;
    public String energyEfficiencyRating;
    public Float weight;
    public List<DeviceCategory> categories;

    public Device(){}

}
