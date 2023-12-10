package at.fhv.layblar.infrastructure;

import at.fhv.layblar.domain.HouseholdUser;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<HouseholdUser, String>{
    
    @Inject
    EntityManager entityManager;
}
