package at.fhv.layblar.domain.readmodel;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import at.fhv.layblar.domain.model.LabeledData;
import at.fhv.layblar.domain.model.Project;
import at.fhv.layblar.domain.model.ProjectMetaData;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class ProjectLabeledData extends PanacheEntity {

    public String labeledDataId;
    public String projectId;
    public String labelId;
    public LocalDateTime validFrom;
    public LocalDateTime validTo;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<ProjectMetaData> metaData;



    public static ProjectLabeledData create(LabeledData labeledData, List<ProjectMetaData> metaData, String labelId, Project project) {
        ProjectLabeledData pld = new ProjectLabeledData();
        pld.labeledDataId = labeledData.labeledDataId;
        pld.projectId = project.projectId;
        pld.labelId = labelId;
        pld.validFrom = labeledData.createdAt;
        pld.validTo = project.endDate;
        pld.metaData = metaData;
        return pld;
    }
    
}
