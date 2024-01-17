package at.fhv.layblar.application.dto;

import at.fhv.layblar.domain.model.ProjectMetaData;

public class ProjectMetaDataDTO {

    public String metaDataId;
    public String metaDataName;
    public Boolean isRequired;
    public String value;

    public ProjectMetaDataDTO(){}

    private ProjectMetaDataDTO(String metaDataId, String metaDataName, Boolean isRequired, String value) {
        this.metaDataId = metaDataId;
        this.metaDataName = metaDataName;
        this.isRequired = isRequired;
        this.value = value;
    }



    public static ProjectMetaDataDTO createProjectMetaDataDTO(ProjectMetaData metaData) {
        return new ProjectMetaDataDTO(metaData.metaDataId, metaData.metaDataName, metaData.isRequired, metaData.value);
    }

}
