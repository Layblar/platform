package at.fhv.layblar.application;

import java.util.List;

import at.fhv.layblar.application.dto.LabelEventDTO;
import at.fhv.layblar.application.dto.LabeledDataDTO;
import at.fhv.layblar.commands.AddLabeledDataCommand;
import at.fhv.layblar.commands.UpdateLabeledDataCommand;
import at.fhv.layblar.utils.exceptions.LabelNotFoundException;
import at.fhv.layblar.utils.exceptions.LabeledDataAlreadyRemovedException;
import at.fhv.layblar.utils.exceptions.LabeledDataNotFoundException;
import at.fhv.layblar.utils.exceptions.NotAuthorizedException;
import at.fhv.layblar.utils.exceptions.ProjectValidityTimeframeException;
import at.fhv.layblar.utils.exceptions.VersionNotMatchingException;

public interface LabeledDataService {

    public List<LabeledDataDTO> getLabeledDataByHousehold(String householdId, String projectId) throws NotAuthorizedException;

    public LabeledDataDTO addLabeledData(AddLabeledDataCommand command) throws NotAuthorizedException, ProjectValidityTimeframeException, LabelNotFoundException;

    public LabeledDataDTO updateLabeledData(UpdateLabeledDataCommand command) throws NotAuthorizedException, LabeledDataNotFoundException, LabeledDataAlreadyRemovedException, ProjectValidityTimeframeException, VersionNotMatchingException, LabelNotFoundException;

    public LabeledDataDTO deleteLabeledData(String labeledDataId) throws NotAuthorizedException, LabeledDataNotFoundException, VersionNotMatchingException;

    public void sendLabelEvent(LabelEventDTO eventDTO) throws NotAuthorizedException, ProjectValidityTimeframeException;
    
}
