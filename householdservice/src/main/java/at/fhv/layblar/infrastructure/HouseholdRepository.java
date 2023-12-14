package at.fhv.layblar.infrastructure;
import at.fhv.layblar.domain.Household;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class HouseholdRepository implements PanacheRepositoryBase<Household, String>{
    
    @Inject
    EntityManager entityManager;
}
