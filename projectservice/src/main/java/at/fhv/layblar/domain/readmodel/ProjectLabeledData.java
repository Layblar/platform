package at.fhv.layblar.domain.readmodel;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import at.fhv.layblar.domain.model.LabeledData;
import at.fhv.layblar.domain.model.ProjectMetaData;
import at.fhv.layblar.domain.model.SmartMeterData;
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
    public List<SmartMeterData> smartMeterData;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<ProjectMetaData> metaData;



    public static ProjectLabeledData create(LabeledData labeledData, List<ProjectMetaData> metaData, String labelId, String projectId, LocalDateTime validFrom, LocalDateTime validTo) {
        ProjectLabeledData pld = new ProjectLabeledData();
        pld.labeledDataId = labeledData.labeledDataId;
        pld.projectId = projectId;
        pld.labelId = labelId;
        pld.validFrom = labeledData.createdAt;
        pld.validFrom = validFrom;
        pld.validTo = validTo;
        pld.metaData = metaData;
        pld.smartMeterData = labeledData.smartMeterData;
        return pld;
    }
    
}
