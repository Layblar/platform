package at.fhv.layblar.projectServiceRouting.model;

import java.util.List;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceDTO;

public class JoinProjectDTO {

    public String householdId;
    public List<ProjectMetaDataDTO> metaData;
    public List<DeviceDTO> devices;
    
}
