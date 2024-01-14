package at.fhv.layblar.application.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import at.fhv.layblar.application.LabelSerivce;
import at.fhv.layblar.application.dto.AddLabeledDataCommand;
import at.fhv.layblar.application.dto.LabeledDataDTO;
import at.fhv.layblar.application.dto.SmartMeterDataDTO;
import at.fhv.layblar.domain.LabeledData;
import at.fhv.layblar.infrastructure.LabelRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;

public class LabelServiceImpl implements LabelSerivce {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    LabelRepository labelRepository;

    @Override
    public List<LabeledDataDTO> getLabelsByHouseholdId(String householdId) {
        if(jsonWebToken.getClaim("householdId").equals(householdId)){
            throw new NotAuthorizedException(null);
        }
        return labelRepository.findByHouseholdId(householdId).stream().map(labeldata -> createLabeledDataDTO(labeldata)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<LabeledDataDTO> createLabel(AddLabeledDataCommand createLabelDTO) {
        List<LabeledData> labeledData = new LinkedList<>();
        for (SmartMeterDataDTO smartMeterDataDTO : createLabelDTO.labelData) {
            labeledData.add(LabeledData.create(jsonWebToken.getClaim("householdId"),
            smartMeterDataDTO, createLabelDTO.label, createLabelDTO.labelMetaData));
        }
        LabeledData.persist(labeledData);
        return labeledData.stream().map(labeldata -> createLabeledDataDTO(labeldata)).collect(Collectors.toList());
    }


    private LabeledDataDTO createLabeledDataDTO(LabeledData labeledData){
        LabeledDataDTO dto = new LabeledDataDTO();
        dto.labelId = labeledData.labelId;
        dto.householdId = labeledData.householdId;
        dto.label = labeledData.label;
        dto.createdAt = labeledData.createdAt;
        SmartMeterDataDTO smdDto = new SmartMeterDataDTO();
        smdDto.sensorId = labeledData.smartMeterData.sensorId;
        smdDto.time = labeledData.smartMeterData.time;
        dto.smartMeterData = smdDto;
        return dto;
    }
    
}
