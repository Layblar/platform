package at.fhv.layblar.domain;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class HouseholdUser extends PanacheEntityBase {
    
    @Id
    public String userId;
    public String email;
    public String firstName;
    public String lastName;
    @ManyToOne(fetch = FetchType.LAZY)
    public Household household;

    public HouseholdUser(){}

    private HouseholdUser(String email, String firstName, String lastName){
        this.userId = UUID.randomUUID().toString();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static HouseholdUser createUser(String email, String firstName, String lastName) {
        return new HouseholdUser(email, firstName, lastName);
    }

}
