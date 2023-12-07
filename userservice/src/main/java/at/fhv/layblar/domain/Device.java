package at.fhv.layblar.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Device extends PanacheEntityBase {

    @Id
    public String id;
    public String name;
    public String type;
    public String household;

    public Device() {}
}
