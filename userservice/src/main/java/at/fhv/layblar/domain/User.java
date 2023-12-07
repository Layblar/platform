package at.fhv.layblar.domain;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User extends PanacheEntityBase {
    
    @Id
    public String userId;
    public String email;
    public String firstName;
    public String lastName;

    public User(){};

    private User(String email, String firstName, String lastName){
        this.userId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static User createUser(String email, String firstName, String lastName) {
        return new User(email, firstName, lastName);
    }

}
