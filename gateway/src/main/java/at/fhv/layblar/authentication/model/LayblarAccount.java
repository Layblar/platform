package at.fhv.layblar.authentication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

@Entity
@UserDefinition
public class LayblarAccount extends PanacheEntityBase   {

    @Id
    public String accountId;
    @Password
    public String password;
    @Username
    public String email;
    public String householdId;
    public String userId;
    public String researcherId;
    @Roles
    public List<String> roles;

    public static LayblarAccount createAccount(String email, String password){
        LayblarAccount user = new LayblarAccount();
        user.accountId = UUID.randomUUID().toString();
        user.email = email;
        user.password = BcryptUtil.bcryptHash(password);
        user.roles = new LinkedList<>();
        return user;
    }

    public LayblarAccount registerUser(String userId, String householdId){
        this.userId = userId;
        this.householdId = householdId;
        this.roles.add("User");
        return this;
    }

    public LayblarAccount registerResearcher(String researcherId){
        this.researcherId = researcherId;
        this.roles.add("Researcher");
        return this;
    }

    // public static LayblarAccount createUser(String userId, String email, String password, String householdId, List<String> roles){
    //     LayblarAccount user = new LayblarAccount();
    //     user.userId = userId;
    //     user.email = email;
    //     user.password = BcryptUtil.bcryptHash(password);
    //     user.householdId = householdId;
    //     user.roles.add("User");
    //     return user;
    // }
}
