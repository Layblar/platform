package at.fhv.layblar.labelServiceRouting.model;

import java.util.List;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceDTO;

public class AddLabeledDataDTO {

    public String householdId;
    public DeviceDTO device;
    public List<SmartMeterDataDTO> smartMeterData;
}
