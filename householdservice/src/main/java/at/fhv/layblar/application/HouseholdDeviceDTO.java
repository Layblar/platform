package at.fhv.layblar.application;

import at.fhv.layblar.domain.Device;

public class HouseholdDeviceDTO {

    public static HouseholdDeviceDTO createDeviceDTO(Device device) {
        HouseholdDeviceDTO deviceDTO = new HouseholdDeviceDTO();
        return deviceDTO;
    }

}
