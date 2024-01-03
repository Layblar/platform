package at.fhv.layblar.application;

import org.eclipse.microprofile.jwt.JsonWebToken;

import at.fhv.layblar.application.dto.CreateHouseholdDTO;
import at.fhv.layblar.application.dto.HouseholdDTO;
import at.fhv.layblar.domain.Household;
import at.fhv.layblar.es.Event;
import at.fhv.layblar.es.HouseholdCreatedEvent;
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
        Event event = new HouseholdCreatedEvent(household);
        event.persist();
        return HouseholdDTO.createHouseholdDTO(household);
    }
    
}
