package at.fhv.layblar.labelServiceRouting.model;

import java.time.LocalDateTime;
import java.util.List;

public class LabeledDataDTO {

    public String labeledDataId;
    public String labelId;
    public String householdId;
    public String projectId;
    public String deviceId;
    public List<SmartMeterDataDTO> smartMeterData;
    public LocalDateTime createdAt;
    
}
