package at.fhv.layblar.commands;

import java.util.List;

import at.fhv.layblar.application.dto.DeviceDTO;
import at.fhv.layblar.application.dto.ProjectMetaDataDTO;

public class JoinProjectCommand {

    public String householdId;
    public List<ProjectMetaDataDTO> metaData;
    public List<DeviceDTO> devices;
    
}
