package at.fhv.layblar.domain.model;

import java.util.UUID;

import at.fhv.layblar.commands.RegisterResearcherCommand;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Researcher extends PanacheEntityBase{

    @Id
    public String researcherId;
    public String email;
    public String firstName;
    public String lastName;
    public String organization;

    public Researcher() {}

    private Researcher(String email, String firstName, String lastName, String organization){
        this.researcherId = UUID.randomUUID().toString();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
    }

    public static Researcher creatResearcher(RegisterResearcherCommand command){
        return new Researcher(command.email, command.firstName, command.lastName, command.organization);
    }
}
