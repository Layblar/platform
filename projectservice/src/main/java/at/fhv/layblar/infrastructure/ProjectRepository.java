package at.fhv.layblar.infrastructure;

import at.fhv.layblar.domain.Project;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class ProjectRepository implements PanacheRepositoryBase<Project, String>{

    @Inject
    EntityManager entityManager;
    
}
