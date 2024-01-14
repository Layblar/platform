package at.fhv.layblar.commands;

import java.util.List;

import at.fhv.layblar.application.dto.SmartMeterDataDTO;

public class AddLabeledDataCommand {

    public String householdId;
    public String projectId;
    public String labelId;
    public String deviceId;
    public List<SmartMeterDataDTO> smartMeterData;
    public Boolean isTimeEvent;
    
}
