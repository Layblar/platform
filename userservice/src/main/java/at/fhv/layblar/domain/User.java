package at.fhv.layblar.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    
    @Id
    public String id;
    public String username;
    public String password;

    public User() {}
}
