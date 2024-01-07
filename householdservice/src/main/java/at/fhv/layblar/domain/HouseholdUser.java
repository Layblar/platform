package at.fhv.layblar.domain;

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

    private HouseholdUser(String userId, String email, String firstName, String lastName){
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public static HouseholdUser createUser(String userId, String email, String firstName, String lastName) {
        return new HouseholdUser(userId, email, firstName, lastName);
    }

}
