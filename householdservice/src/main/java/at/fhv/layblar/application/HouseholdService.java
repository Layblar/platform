package at.fhv.layblar.application;

import at.fhv.layblar.application.dto.CreateHouseholdDTO;
import at.fhv.layblar.application.dto.HouseholdDTO;

public interface HouseholdService {
    
    public HouseholdDTO createHousehold(CreateHouseholdDTO CreateHouseholdDTO);
}
