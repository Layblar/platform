package at.fhv.layblar.projectServiceRouting.model;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectInfoDTO {

    public String projectId;
    public String projectName;
    public String projectDescription;
    public String projectDataUseDeclartion;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public LocalDateTime created;
    public List<ProjectMetaDataDTO> metaData;
    public List<LabelDTO> labels;
}
