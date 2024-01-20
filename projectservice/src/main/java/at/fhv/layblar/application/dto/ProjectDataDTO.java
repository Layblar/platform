package at.fhv.layblar.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.readmodel.ProjectLabeledData;

public class ProjectDataDTO {

    public String labeledDataId;
    public String projectId;
    public String labelId;
    public String labelName;
    public String deviceCategoryId;
    public String deviceCategoryName;
    public LocalDateTime validFrom;
    public LocalDateTime validTo;
    public LocalDateTime readingTime;
    public Float v1_7_0;
    public Float v1_8_0;
    public Float v2_7_0;
    public Float v2_8_0;
    public Float v3_8_0;
    public Float v4_8_0;
    public Float v16_7_0;
    public Float v31_7_0;
    public Float v32_7_0;
    public Float v51_7_0;
    public Float v52_7_0;
    public Float v71_7_0;
    public Float v72_7_0;
    public List<ProjectMetaDataDTO> metaData;

    public ProjectDataDTO(){}

    public ProjectDataDTO(String labeledDataId, String projectId, String labelId, String labelName,
            String deviceCategoryId, String deviceCategoryName, LocalDateTime validFrom, LocalDateTime validTo,
            LocalDateTime readingTime, Float v1_7_0, Float v1_8_0, Float v2_7_0, Float v2_8_0, Float v3_8_0,
            Float v4_8_0, Float v16_7_0, Float v31_7_0, Float v32_7_0, Float v51_7_0, Float v52_7_0, Float v71_7_0,
            Float v72_7_0, List<ProjectMetaDataDTO> metaData) {
        this.labeledDataId = labeledDataId;
        this.projectId = projectId;
        this.labelId = labelId;
        this.labelName = labelName;
        this.deviceCategoryId = deviceCategoryId;
        this.deviceCategoryName = deviceCategoryName;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.readingTime = readingTime;
        this.v1_7_0 = v1_7_0;
        this.v1_8_0 = v1_8_0;
        this.v2_7_0 = v2_7_0;
        this.v2_8_0 = v2_8_0;
        this.v3_8_0 = v3_8_0;
        this.v4_8_0 = v4_8_0;
        this.v16_7_0 = v16_7_0;
        this.v31_7_0 = v31_7_0;
        this.v32_7_0 = v32_7_0;
        this.v51_7_0 = v51_7_0;
        this.v52_7_0 = v52_7_0;
        this.v71_7_0 = v71_7_0;
        this.v72_7_0 = v72_7_0;
        this.metaData = metaData;
    }

    public static ProjectDataDTO createProjectDataDTO(ProjectLabeledData projectLabeledData){
        return new ProjectDataDTO(projectLabeledData.labeledDataId, projectLabeledData.projectId, projectLabeledData.labelId, projectLabeledData.labelName, projectLabeledData.deviceCategoryId, projectLabeledData.deviceCategoryName, projectLabeledData.validFrom, projectLabeledData.validTo, projectLabeledData.readingTime, projectLabeledData.v1_7_0, projectLabeledData.v1_8_0, projectLabeledData.v2_7_0, projectLabeledData.v2_8_0, projectLabeledData.v3_8_0, projectLabeledData.v4_8_0, projectLabeledData.v16_7_0, projectLabeledData.v31_7_0, projectLabeledData.v32_7_0, projectLabeledData.v51_7_0, projectLabeledData.v52_7_0, projectLabeledData.v71_7_0, projectLabeledData.v72_7_0,
        projectLabeledData.metaData.stream().map(meta -> ProjectMetaDataDTO.createProjectMetaDataDTO(meta)).collect(Collectors.toList()));
    }

}
