package at.fhv.layblar.infrastructure;

import at.fhv.layblar.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class UserRepository implements PanacheRepositoryBase<User, String>{
    
    @Inject
    EntityManager entityManager;
}
