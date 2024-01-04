package at.fhv.layblar.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Names extends PanacheEntityBase {

    @Id
    public String userId;
    public String firstName;
    public String lastName;
    
}
