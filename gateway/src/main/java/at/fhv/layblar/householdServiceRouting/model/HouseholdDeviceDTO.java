package at.fhv.layblar.householdServiceRouting.model;

import java.util.List;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceCategoryDTO;

public class HouseholdDeviceDTO {

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
