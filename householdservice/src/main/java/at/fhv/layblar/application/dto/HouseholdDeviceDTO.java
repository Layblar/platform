package at.fhv.layblar.application.dto;

import at.fhv.layblar.domain.Device;

public class HouseholdDeviceDTO {

    public static HouseholdDeviceDTO createDeviceDTO(Device device) {
        HouseholdDeviceDTO deviceDTO = new HouseholdDeviceDTO();
        return deviceDTO;
    }

}
