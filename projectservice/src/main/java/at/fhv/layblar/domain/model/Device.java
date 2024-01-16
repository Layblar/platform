package at.fhv.layblar.domain.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Device {
    
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

}
