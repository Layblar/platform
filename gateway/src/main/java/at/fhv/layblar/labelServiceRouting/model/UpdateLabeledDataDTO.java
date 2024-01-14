package at.fhv.layblar.labelServiceRouting.model;

import java.util.List;

public class UpdateLabeledDataDTO {

    public String labeledDataId;
    public String householdId;
    public String projectId;
    public String labelId;
    public String deviceId;
    public List<SmartMeterDataDTO> smartMeterData;

}
