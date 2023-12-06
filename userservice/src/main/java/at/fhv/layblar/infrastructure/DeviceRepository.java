package at.fhv.layblar.infrastructure;

import at.fhv.layblar.domain.Device;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class DeviceRepository implements PanacheRepositoryBase<Device, String> {
    
    @Inject
    EntityManager entityManager;
}
