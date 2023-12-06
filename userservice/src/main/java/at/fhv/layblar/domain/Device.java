package at.fhv.layblar.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Device {

    @Id
    public String id;
    public String name;
    public String type;
    public String household;

    public Device() {}
}
