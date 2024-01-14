package at.fhv.layblar.application;

import at.fhv.layblar.application.dto.AddLabeledDataCommand;

public interface LabelSerivce {

    Object getLabelsByHouseholdId(String householdId);

    Object createLabel(AddLabeledDataCommand createLabelDTO);

}
