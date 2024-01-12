package at.fhv.layblar.commands;

import java.time.LocalDateTime;
import java.util.List;

import at.fhv.layblar.application.dto.LabelDTO;
import at.fhv.layblar.application.dto.ProjectMetaDataDTO;

public class UpdateProjectCommand {

    public String projectId;
    public String projectName;
    public String projectDescription;
    public String projectDataUseDeclartion;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public List<ProjectMetaDataDTO> metaData;
    public List<LabelDTO> labels;
    
}
