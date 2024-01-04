package at.fhv.layblar.application;

import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.Device;

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

    private DeviceDTO(String deviceId, String deviceName, String deviceDescription, String manufacturer,
            String modelNumber, Integer powerDraw, String energyEfficiencyRating, Float weight, List<DeviceCategoryDTO> categoryDTOs) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDescription = deviceDescription;
        this.manufacturer = manufacturer;
        this.modelNumber = modelNumber;
        this.powerDraw = powerDraw;
        this.energyEfficiencyRating = energyEfficiencyRating;
        this.weight = weight;
        this.categories = categoryDTOs;
    }

    public static DeviceDTO createDTO(Device device) {
        return new DeviceDTO(device.deviceId, device.deviceName, device.deviceDescription, device.manufacturer,
        device.modelNumber, device.powerDraw, device.energyEfficiencyRating, device.weight,
        device.deviceCategory.stream().map(category -> DeviceCategoryDTO.creatDeviceCategoryDTO(category)).collect(Collectors.toList()));
    }

}
