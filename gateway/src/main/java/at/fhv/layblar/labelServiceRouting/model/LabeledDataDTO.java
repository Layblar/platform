package at.fhv.layblar.labelServiceRouting.model;

import java.time.LocalDateTime;
import java.util.List;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceDTO;

public class LabeledDataDTO {

    public String labeledDataId;
    public String householdId;
    public DeviceDTO device;
    public List<SmartMeterDataDTO> smartMeterData;
    public LocalDateTime createdAt;
    
}
