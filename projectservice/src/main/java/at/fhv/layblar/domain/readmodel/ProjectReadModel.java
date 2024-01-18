package at.fhv.layblar.domain.readmodel;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import at.fhv.layblar.domain.model.ProjectMetaData;
import at.fhv.layblar.domain.model.ProjectParticipant;
import at.fhv.layblar.domain.model.Researcher;
import at.fhv.layblar.events.ProjectCreatedEvent;
import at.fhv.layblar.events.ProjectJoinedEvent;
import at.fhv.layblar.events.ProjectUpdatedEvent;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@NamedQueries({
        @NamedQuery(name = "ProjectReadModel.byParticipant", query = "FROM ProjectReadModel p JOIN p.participants participant WHERE participant.householdId = ?1"),
        @NamedQuery(name = "Project.byResearcher", query = "FROM ProjectReadModel p JOIN p.researcher researcher WHERE researcher.researcherId = ?1"),
        @NamedQuery(name = "Project.byLabeledDataDeviceCategories", query = 
                "SELECT p.projectId, p.endDate, label.labelId, participant.householdMetaData " +
                "FROM ProjectReadModel p " +
                "JOIN p.participants participant " +
                "JOIN p.labels label " +
                "JOIN p.labels.categories category " +
                "WHERE participant.householdId = :householdId AND category.deviceCategoryId IN (:categoryIds)")
})
public class ProjectReadModel extends PanacheEntityBase {

    @Id
    public String projectId;
    public String projectName;
    public String projectDescription;
    public String projectDataUseDeclartion;
    @ManyToOne
    @JoinColumn(name = "researcherId")
    public Researcher researcher;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<ProjectMetaData> metaDataInfo;
    @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
    public List<LabelReadModel> labels;
    public LocalDateTime createdAt;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<ProjectParticipant> participants;

    public ProjectReadModel() {
    }

    public void apply(ProjectCreatedEvent event) {
        this.projectId = event.getProjectId();
        this.projectName = event.getProjectName();
        this.projectDescription = event.getProjectDescription();
        this.projectDataUseDeclartion = event.getProjectDataUseDeclaration();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.metaDataInfo = event.getMetaDataInfo();
        this.labels = new LinkedList<>();
        event.getLabels().stream().forEach(label -> addLabelToProject(LabelReadModel.createFromLabel(label)));
        this.createdAt = event.getCreatedAt();
        this.participants = new LinkedList<>();
        this.researcher = event.getResearcher();
    }

    public void apply(ProjectUpdatedEvent event) {
        this.projectName = event.getProjectName();
        this.projectDescription = event.getProjectDescription();
        this.projectDataUseDeclartion = event.getProjectDataUseDeclaration();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        updateLabels(event.getLabels().stream().map(label -> LabelReadModel.createFromLabel(label))
                .collect(Collectors.toList()));
        updateMetaData(event.getMetaDataInfo());
    }

    public void apply(ProjectJoinedEvent event) {
        this.participants
                .add(ProjectParticipant.createParticipant(event.getHouseholdId(), event.getHouseholdMetaData()));
    }

    private void addLabelToProject(LabelReadModel label) {
        label.project = this;
        this.labels.add(label);
    }

    private void updateLabels(List<LabelReadModel> labels) {
        for (LabelReadModel label : labels) {
            this.labels.remove(label);
            addLabelToProject(label);
        }
    }

    private void updateMetaData(List<ProjectMetaData> metaData) {
        for (ProjectMetaData data : metaData) {
            this.metaDataInfo.remove(data);
            this.metaDataInfo.add(data);
        }
    }

    public boolean isProjectParticipant(String householdId) {
        return participants.stream().anyMatch(participant -> participant.householdId.equals(householdId));
    }

    public boolean isActive() {
        return (LocalDateTime.now().isAfter(startDate) && LocalDateTime.now().isBefore(endDate));
    }

    public boolean hasLabel(String labelId) {
        return labels.stream().anyMatch(label -> label.labelId.equals(labelId));
    }

    public static List<ProjectReadModel> findByParticipant(String householdId) {
        return find("#Project.byParticipant", householdId).list();
    }

    public static List<ProjectReadModel> findByResearcherId(String researcherId) {
        return find("#Project.byResearcher", researcherId).list();
    }

    public static List<ViableProject> findProjectWithMatchingLabels(String householdId,
            List<String> deviceCategoryIds) {
        return find("#Project.byLabeledDataDeviceCategories",
                Parameters.with("householdId", householdId).and("categoryIds", deviceCategoryIds)).project(ViableProject.class).list();
    }

}
