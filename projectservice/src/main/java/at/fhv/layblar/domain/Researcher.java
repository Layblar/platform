package at.fhv.layblar.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Researcher {

    @Id
    public String id;
    public String name;
    public String organization;

    public Researcher() {}
}
