package at.fhv.layblar.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EventTracking extends PanacheEntityBase {

    @Id
    public String eventId;
    
}
