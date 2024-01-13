package at.fhv.layblar.domain.readmodel;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import at.fhv.layblar.domain.model.Label;
import at.fhv.layblar.domain.model.ProjectMetaData;
import at.fhv.layblar.domain.model.ProjectParticipant;
import at.fhv.layblar.events.ProjectCreatedEvent;
import at.fhv.layblar.events.ProjectJoinedEvent;
import at.fhv.layblar.events.ProjectUpdatedEvent;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProjectReadModel extends PanacheEntityBase {

    @Id
    public String projectId;
    public String projectName;
    public String projectDescription;
    public String projectDataUseDeclartion;
    public String researcherId;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<ProjectMetaData> metaDataInfo;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<Label> labels;
    public LocalDateTime createdAt;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<ProjectParticipant> participants;

    
    public ProjectReadModel() {}


    public void apply(ProjectCreatedEvent event) {
        this.projectId = event.getProjectId();
        this.projectName = event.getProjectName();
        this.projectDescription = event.getProjectDescription();
        this.projectDataUseDeclartion = event.getProjectDataUseDeclaration();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.metaDataInfo = event.getMetaDataInfo();
        this.labels = event.getLabels();
        this.createdAt = event.getCreatedAt();
        this.participants = new LinkedList<>();
        this.researcherId = event.getResearcher().researcherId;
    }


    public void apply(ProjectUpdatedEvent event) {
        this.projectName = event.getProjectName();
        this.projectDescription = event.getProjectDescription();
        this.projectDataUseDeclartion = event.getProjectDataUseDeclaration();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        updateLabels(event.getLabels());
        updateMetaData(event.getMetaDataInfo());
    }

    public void apply(ProjectJoinedEvent event) {
        this.participants.add(ProjectParticipant.createParticipant(event.getHouseholdId(), event.getHouseholdMetaData()));
    }

    private void updateLabels(List<Label> labels){
        for (Label label : labels) {
            this.labels.remove(label);
            this.labels.add(label);
        }
    }

    private void updateMetaData(List<ProjectMetaData> metaData){
        for (ProjectMetaData data : metaData) {
            this.metaDataInfo.remove(data);
            this.metaDataInfo.add(data);
        }
    }

}
