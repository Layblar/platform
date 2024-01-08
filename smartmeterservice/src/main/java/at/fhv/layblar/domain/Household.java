package at.fhv.layblar.domain;

import java.util.LinkedList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Household extends PanacheEntityBase {
    
    @Id
    public String householdId;
    public List<String> smartMeterId = new LinkedList<>();

}
