package at.fhv.layblar.authentication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.util.List;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

@Entity
@UserDefinition
public class LayblarAccount extends PanacheEntityBase   {

    @Id
    public String userId;
    @Password
    public String password;
    @Username
    public String email;
    public String householdId;
    @Roles
    public List<String> roles;

    public static LayblarAccount createUser(String userId, String email, String password, String householdId, List<String> roles){
        LayblarAccount user = new LayblarAccount();
        user.userId = userId;
        user.email = email;
        user.password = BcryptUtil.bcryptHash(password);
        user.householdId = householdId;
        user.roles = roles;
        return user;
    }
}
