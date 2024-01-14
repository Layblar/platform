package at.fhv.layblar.application;

import java.util.List;

import at.fhv.layblar.application.dto.LabeledDataDTO;
import at.fhv.layblar.commands.AddLabeledDataCommand;
import at.fhv.layblar.commands.UpdateLabeledDataCommand;
import at.fhv.layblar.utils.exceptions.NotAuthorizedException;
import at.fhv.layblar.utils.exceptions.ProjectValidityTimeframeException;

public interface LabeledDataService {

    public List<LabeledDataDTO> getLabeledDataByHousehold(String householdId, String projectId) throws NotAuthorizedException;

    public LabeledDataDTO addLabeledData(AddLabeledDataCommand command) throws NotAuthorizedException, ProjectValidityTimeframeException;

    public List<LabeledDataDTO> updateLabeledData(UpdateLabeledDataCommand command) throws NotAuthorizedException;

    public Object deleteLabeledData(String labeledDataId);
    
}
