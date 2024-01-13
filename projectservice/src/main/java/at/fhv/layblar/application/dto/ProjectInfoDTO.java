package at.fhv.layblar.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.model.Project;
import at.fhv.layblar.domain.readmodel.ProjectReadModel;

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

    public ProjectInfoDTO() {
    }

    private ProjectInfoDTO(String projectId, String projectName, String projectDescription,
            String projectDataUseDeclartion, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime created,
            List<ProjectMetaDataDTO> metaData, List<LabelDTO> labels) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectDataUseDeclartion = projectDataUseDeclartion;
        this.startDate = startDate;
        this.endDate = endDate;
        this.created = created;
        this.metaData = metaData;
        this.labels = labels;
    }

    public static ProjectInfoDTO createProjectInfoDTO(Project project) {
        return new ProjectInfoDTO(project.projectId, project.projectName, project.projectDescription,
                project.projectDataUseDeclartion, project.startDate, project.endDate, project.createdAt,
                project.metaDataInfo.stream().map(metaData -> ProjectMetaDataDTO.createProjectMetaDataDTO(metaData))
                        .collect(Collectors.toList()),
                project.labels.stream().map(label -> LabelDTO.createLabelDTO(label)).collect(Collectors.toList()));

    }

    public static ProjectInfoDTO createProjectInfoDTO(ProjectReadModel project) {
        return new ProjectInfoDTO(project.projectId, project.projectName, project.projectDescription,
                project.projectDataUseDeclartion, project.startDate, project.endDate, project.createdAt,
                project.metaDataInfo.stream().map(metaData -> ProjectMetaDataDTO.createProjectMetaDataDTO(metaData))
                        .collect(Collectors.toList()),
                project.labels.stream().map(label -> LabelDTO.createLabelDTO(label)).collect(Collectors.toList()));

    }
}
