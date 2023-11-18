package at.fhv.layblar.domain;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Project extends PanacheEntityBase{

    @Id
    public String id;
    public String name;
    public String description;
    public String startDate;
    public String endDate;
    public String researcherId;
    public List<String> participants;

    
    public Project() {}

}
