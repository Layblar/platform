package at.fhv.layblar.labelServiceRouting.model;

import java.time.LocalDateTime;

public class LabeledDataDTO {

    public String labelId;
    public String householdId;
    public SmartMeterDataDTO smartMeterData;
    public String label;
    public LocalDateTime createdAt;
    
}
