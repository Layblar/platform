package at.fhv.layblar.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import at.fhv.layblar.es.HouseholdCreatedEvent;
import at.fhv.layblar.es.HouseholdJoinedEvent;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Household extends PanacheEntityBase {
    @Id
    public String householdId;
    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<HouseholdUser> users;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Device> devices;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<SmartMeter> smartMeters;

    public Household(){}

    private Household(HouseholdUser user) {
        this.householdId = UUID.randomUUID().toString();
        users = new LinkedList<>();
        users.add(user);
        devices = new LinkedList<>();
    }

    public static Household createHouseHold(String email, String firstName, String lastName) {
        HouseholdUser user = HouseholdUser.createUser(email, firstName, lastName);
        return new Household(user);
    }

    public void apply(HouseholdCreatedEvent event) {

    }

    public void apply(HouseholdJoinedEvent event) {
        
    }

}
