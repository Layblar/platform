package at.fhv.layblar.domain.readmodel;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
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
        @NamedQuery(name = "Project.byParticipant", query = "FROM ProjectReadModel p JOIN p.participants participants WHERE participants.householdId = ?1"),
        @NamedQuery(name = "Project.byResearcher", query = "FROM ProjectReadModel p JOIN p.researcher researcher WHERE researcher.researcherId = ?1"),
        @NamedQuery(name = "Project.isJoinable", query = "FROM ProjectReadModel p LEFT OUTER JOIN p.participants participants WHERE (participants.householdId IS NULL OR participants.householdId != ?1) AND p.startDate <= ?2 AND p.endDate >= ?2"),
        @NamedQuery(name = "Project.byLabeledDataDeviceCategories", query = 
                "SELECT p.projectId, label.labelId, label.labelName, category.deviceCategoryId, category.deviceCategoryName, p.endDate, participant.householdMetaData " +
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
    @OneToMany(orphanRemoval=true, cascade = CascadeType.ALL)
    @JoinColumn(name="projectId")
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
        event.getLabels().stream().forEach(label -> updateLabel(LabelReadModel.createFromLabel(label)));
        this.metaDataInfo = event.getMetaDataInfo();
    }

    public void apply(ProjectJoinedEvent event) {
        this.participants
                .add(ProjectParticipant.createParticipant(event.getHouseholdId(), event.getHouseholdMetaData()));
    }

    private void addLabelToProject(LabelReadModel label) {
        //label.project = this;
        this.labels.add(label);
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

    public static List<ProjectReadModel> findJoinableProjects(String householdId) {
        return find("#Project.isJoinable", householdId, LocalDateTime.now()).list();
    }

    public void updateLabel(LabelReadModel label) {
        this.labels.remove(label);
        this.labels.add(label);
    }

    public void addLabels(List<LabelReadModel> labels2) {
        for (LabelReadModel labelReadModel : labels2) {
            updateLabel(labelReadModel);
        }
    }

}
