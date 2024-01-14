package at.fhv.layblar.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import at.fhv.layblar.domain.model.LabeledData;

public class LabeledDataDTO {
    
    public String labeledDataId;
    public String labelId;
    public String householdId;
    public String projectId;
    public String deviceId;
    public List<SmartMeterDataDTO> smartMeterData;
    public LocalDateTime createdAt;

    public LabeledDataDTO(){}

    private LabeledDataDTO(String labeledDataId, String labelId, String householdId, String projectId, String deviceId,
            List<SmartMeterDataDTO> smartMeterData, LocalDateTime createdAt) {
        this.labeledDataId = labeledDataId;
        this.labelId = labelId;
        this.householdId = householdId;
        this.projectId = projectId;
        this.deviceId = deviceId;
        this.smartMeterData = smartMeterData;
        this.createdAt = createdAt;
    }



    public static LabeledDataDTO createLabeledDataDTO(LabeledData labeledData){
        return new LabeledDataDTO(labeledData.labeledDataId, labeledData.labelId, labeledData.householdId, labeledData.projectId, labeledData.deviceId,
        null, labeledData.createdAt);
    }
}
