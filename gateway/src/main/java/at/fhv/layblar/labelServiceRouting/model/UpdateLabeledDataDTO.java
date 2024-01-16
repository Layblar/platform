package at.fhv.layblar.labelServiceRouting.model;

import java.util.List;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceDTO;

public class UpdateLabeledDataDTO {

    public String labeledDataId;
    public DeviceDTO deviceDTO;
    public List<SmartMeterDataDTO> smartMeterData;

}
