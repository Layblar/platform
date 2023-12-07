package at.fhv.layblar.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Household extends PanacheEntityBase {
    @Id
    public String householdId;
    public List<User> users;
    public List<Device> devices;

    public Household(){}

    private Household(User user) {
        this.householdId = UUID.randomUUID().toString();
        users = new LinkedList<>();
        users.add(user);
        devices = new LinkedList<>();
    }

    public static Household createHouseHold(String email, String firstName, String lastName) {
        User user = User.createUser(email, firstName, lastName);
        return new Household(user);
    }

}
