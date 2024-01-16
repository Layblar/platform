package at.fhv.layblar.domain.model;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProjectParticipant extends PanacheEntityBase {

    @Id
    public String householdId;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<ProjectMetaData> householdMetaData;   
    
    public ProjectParticipant(){}

    private ProjectParticipant(String householdId, List<ProjectMetaData> householdMetaData) {
        this.householdId = householdId;
        this.householdMetaData = householdMetaData;
    }

    public static ProjectParticipant createParticipant(String householdId, List<ProjectMetaData> householdMetaData) {
        return new ProjectParticipant(householdId, householdMetaData);
    }

}
