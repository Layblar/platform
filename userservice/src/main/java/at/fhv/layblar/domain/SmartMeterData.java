package at.fhv.layblar.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SmartMeterData extends PanacheEntityBase {
    @Id
    public String id;

    public SmartMeterData() {}
}
