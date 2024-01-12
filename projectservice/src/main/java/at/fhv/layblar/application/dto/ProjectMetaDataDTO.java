package at.fhv.layblar.application.dto;

import at.fhv.layblar.domain.ProjectMetaData;

public class ProjectMetaDataDTO {

    public String metaDataId;
    public String metaDataName;
    public String type;
    public String value;

    public ProjectMetaDataDTO(){}

    private ProjectMetaDataDTO(String metaDataId, String metaDataName, String type, String value) {
        this.metaDataId = metaDataId;
        this.metaDataName = metaDataName;
        this.type = type;
        this.value = value;
    }



    public static ProjectMetaDataDTO createProjectMetaDataDTO(ProjectMetaData metaData) {
        return new ProjectMetaDataDTO(metaData.metaDataId, metaData.metaDataName, metaData.type, metaData.value);
    }

}
