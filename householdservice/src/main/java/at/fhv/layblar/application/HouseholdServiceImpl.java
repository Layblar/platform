package at.fhv.layblar.application;

import org.eclipse.microprofile.jwt.JsonWebToken;

import at.fhv.layblar.domain.Household;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class HouseholdServiceImpl implements HouseholdService {

    @Inject
    JsonWebToken jsonWebToken;

    @Override
    @Transactional
    public HouseholdDTO createHousehold(CreateHouseholdDTO createHouseholdDTO) {
        Household household = Household.createHouseHold(createHouseholdDTO.email, createHouseholdDTO.firstName, createHouseholdDTO.lastName);
        household.persist();
        return HouseholdDTO.createHouseholdDTO(household);
    }
    
}
