package at.fhv.layblar.domain.readmodel;

import java.time.LocalDateTime;
import java.util.List;

import at.fhv.layblar.domain.model.ProjectMetaData;

// Projection class for select statement of ProjectReadModel
public class ViableProject {

    public final String projectId;
    public final String labelId;
    public final String labelName;
    public final String deviceCategoryId;
    public final String deviceCategoryName;
    public final LocalDateTime projectEndDate;
    public final List<ProjectMetaData> householdMetaData;

    public ViableProject(String projectId, String labelId, String labelName, String deviceCategoryId, String deviceCategoryName, LocalDateTime endDate, List<ProjectMetaData> householdMetaData){
        this.projectId = projectId;
        this.labelId = labelId;
        this.labelName = labelName;
        this.deviceCategoryId = deviceCategoryId;
        this.deviceCategoryName = deviceCategoryName;
        this.projectEndDate = endDate;
        this.householdMetaData = householdMetaData;
    }

}
