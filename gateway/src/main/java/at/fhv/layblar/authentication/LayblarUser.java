package at.fhv.layblar.authentication;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LayblarUser extends PanacheEntityBase   {

    @Id
    public String userId;
    public String username;
    public String password;
    public List<String> roles;
    
}
