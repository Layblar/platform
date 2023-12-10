package at.fhv.layblar.authentication.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LayblarUser extends PanacheEntityBase   {

    @Id
    public String userId;
    public String password;
    public String email;
    public String householdId;

    @Override
    public String toString() {
        return "LayblarUser [userId=" + userId + ", password=" + password + ", email=" + email + ", householdId="
                + householdId + "]";
    }

}
