package at.fhv.layblar.application.dto;

import at.fhv.layblar.domain.SmartMeter;

public class SmartMeterDTO {

    public String smartMeterId;

    public static SmartMeterDTO createSmartMeterDTO(SmartMeter smartMeter) {
        SmartMeterDTO dto = new SmartMeterDTO();
        dto.smartMeterId = smartMeter.smartMeterId;
        return dto;
    }
    
}
