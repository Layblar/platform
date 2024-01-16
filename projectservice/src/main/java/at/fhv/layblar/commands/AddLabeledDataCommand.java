package at.fhv.layblar.commands;

import java.util.List;

import at.fhv.layblar.application.dto.DeviceDTO;
import at.fhv.layblar.application.dto.SmartMeterDataDTO;

public class AddLabeledDataCommand {

    public String householdId;
    public DeviceDTO device;
    public List<SmartMeterDataDTO> smartMeterData;
    
}
