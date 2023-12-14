package at.fhv.layblar.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Device extends PanacheEntityBase {

    @Id
    public String deviceId;
    public String name;
    public String type;

    public Device(){}

}
