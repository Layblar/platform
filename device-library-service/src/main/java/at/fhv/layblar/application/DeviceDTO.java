package at.fhv.layblar.application;

import at.fhv.layblar.domain.Device;

public class DeviceDTO {

    private String name;
    private String manufacturer;

    private DeviceDTO(String name, String manufacturer){
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }
    public String getManufacturer() {
        return manufacturer;
    }

    public static DeviceDTO createDTO(Device device) {
        return new DeviceDTO(device.displayName, device.manufacturer);
    }

}
