package at.fhv.layblar.application;

import at.fhv.layblar.application.dto.CreateLabelDTO;

public interface LabelSerivce {

    Object getLabelsByHouseholdId(String householdId);

    Object createLabel(CreateLabelDTO createLabelDTO);

}
