package at.fhv.layblar.projectServiceRouting.model;

import java.time.LocalDateTime;
import java.util.List;

public class CreateProjectDTO {

    public String projectName;
    public String projectDescription;
    public String projectDataUseDeclartion;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public List<ProjectMetaDataDTO> metaData;
    public List<LabelDTO> labels;
    
}
