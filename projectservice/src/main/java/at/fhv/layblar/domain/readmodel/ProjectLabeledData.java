package at.fhv.layblar.domain.readmodel;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import at.fhv.layblar.domain.model.ProjectMetaData;
import at.fhv.layblar.domain.model.SmartMeterData;
import at.fhv.layblar.events.LabeledDataEvent;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class ProjectLabeledData extends PanacheEntity {

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
    @JdbcTypeCode(SqlTypes.JSON)
    public List<ProjectMetaData> metaData;
    public Integer batchNumber;

    public ProjectLabeledData(){}

    private ProjectLabeledData(String labeledDataId, String projectId, String labelId, String labelName,
            String deviceCategoryId, String deviceCategoryName, LocalDateTime validFrom, LocalDateTime validTo,
            SmartMeterData smartMeterData, List<ProjectMetaData> metaData, Integer batchNumber) {
        this.labeledDataId = labeledDataId;
        this.projectId = projectId;
        this.labelId = labelId;
        this.labelName = labelName;
        this.deviceCategoryId = deviceCategoryId;
        this.deviceCategoryName = deviceCategoryName;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.readingTime = LocalDateTime.parse(smartMeterData.time);
        this.v1_7_0 = smartMeterData.v1_7_0;
        this.v1_8_0 = smartMeterData.v1_8_0;
        this.v2_7_0 = smartMeterData.v2_7_0;
        this.v2_8_0 = smartMeterData.v2_8_0;
        this.v3_8_0 = smartMeterData.v3_8_0;
        this.v4_8_0 = smartMeterData.v4_8_0;
        this.v16_7_0 = smartMeterData.v16_7_0;
        this.v31_7_0 = smartMeterData.v31_7_0;
        this.v32_7_0 = smartMeterData.v32_7_0;
        this.v51_7_0 = smartMeterData.v51_7_0;
        this.v52_7_0 = smartMeterData.v52_7_0;
        this.v71_7_0 = smartMeterData.v71_7_0;
        this.v72_7_0 = smartMeterData.v72_7_0;
        this.metaData = metaData;
        this.batchNumber = batchNumber;
    }

    public static Collection<? extends ProjectLabeledData> create(LabeledDataReadModel labeledData, ViableProject viableProject,
            LabeledDataEvent event) {
        
        return labeledData.smartMeterData.stream().map(smd -> new ProjectLabeledData(labeledData.labeledDataId, viableProject.projectId, viableProject.labelId, viableProject.labelName, viableProject.deviceCategoryId, viableProject.deviceCategoryName, event.timestamp, viableProject.projectEndDate, smd, viableProject.householdMetaData, event.getBatchNumber())
        ).collect(Collectors.toList());
    }



    
    
}
