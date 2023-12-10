package at.fhv.layblar.infrastructure;

import at.fhv.layblar.domain.Device;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class DeviceRepository implements PanacheRepositoryBase<Device, String> {
    
    @Inject
    EntityManager entityManager;
}
