package at.fhv.layblar.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.Device;

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

    public HouseholdDeviceDTO(){}

    private HouseholdDeviceDTO(String deviceId, String deviceName, String deviceDescription, String manufacturer,
            String modelNumber, Integer powerDraw, String energyEfficiencyRating, Float weight,
            List<DeviceCategoryDTO> categories) {
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



    public static HouseholdDeviceDTO createDeviceDTO(Device device) {
        List<DeviceCategoryDTO> categoryDTOs = device.categories.stream().map(deviceCategory -> DeviceCategoryDTO.createDeviceCategoryDTO(deviceCategory)).collect(Collectors.toList());
    return new HouseholdDeviceDTO(device.deviceId, 
    device.deviceName, device.deviceDescription, device.manufacturer,
     device.modelNumber, device.powerDraw, device.energyEfficiencyRating, device.weight,
     categoryDTOs);
    }

}
