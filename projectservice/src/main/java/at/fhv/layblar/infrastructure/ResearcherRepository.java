package at.fhv.layblar.infrastructure;

import at.fhv.layblar.domain.Researcher;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ResearcherRepository implements PanacheRepositoryBase<Researcher, String>{

    @Inject
    EntityManager entityManager;
    
}
