package at.fhv.layblar.domain;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Household extends PanacheEntityBase{
    @Id
    public String id;
    public String name;
    public String address;
    public List<String> users;
    public List<String> devices;

    public Household() {}
}
