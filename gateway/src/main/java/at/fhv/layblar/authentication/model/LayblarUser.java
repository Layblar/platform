package at.fhv.layblar.authentication.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.UserDefinition;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@UserDefinition
public class LayblarUser extends PanacheEntityBase   {

    @Id
    public String userId;
    @Password
    public String password;
    public String email;
    public String householdId;

}
