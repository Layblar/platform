package at.fhv.layblar.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.model.LabeledData;
import at.fhv.layblar.domain.readmodel.LabeledDataReadModel;

public class LabeledDataDTO {
    
    public String labeledDataId;
    public String householdId;
    public DeviceDTO device;
    public List<SmartMeterDataDTO> smartMeterData;
    public LocalDateTime createdAt;

    public LabeledDataDTO(){}

    private LabeledDataDTO(String labeledDataId, String householdId, DeviceDTO device,
            List<SmartMeterDataDTO> smartMeterData, LocalDateTime createdAt) {
        this.labeledDataId = labeledDataId;
        this.householdId = householdId;
        this.device = device;
        this.smartMeterData = smartMeterData;
        this.createdAt = createdAt;
    }

    public static LabeledDataDTO createLabeledDataDTO(LabeledData labeledData){
        return new LabeledDataDTO(labeledData.labeledDataId, labeledData.householdId, DeviceDTO.createDTO(labeledData.device),
        labeledData.smartMeterData.stream().map(data -> SmartMeterDataDTO.createSmartMeterDataDTO(data)).collect(Collectors.toList()), labeledData.createdAt);
    }

    public static LabeledDataDTO createLabeledDataDTO(LabeledDataReadModel labeledData){
        return new LabeledDataDTO(labeledData.labeledDataId, labeledData.householdId, DeviceDTO.createDTO(labeledData.device),
        labeledData.smartMeterData.stream().map(data -> SmartMeterDataDTO.createSmartMeterDataDTO(data)).collect(Collectors.toList()), labeledData.createdAt);
    }
}
