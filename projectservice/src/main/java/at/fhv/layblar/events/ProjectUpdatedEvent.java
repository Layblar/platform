package at.fhv.layblar.events;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.UpdateProjectCommand;
import at.fhv.layblar.domain.Label;
import at.fhv.layblar.domain.Project;
import at.fhv.layblar.domain.ProjectMetaData;
import jakarta.persistence.Entity;

@Entity
public class ProjectUpdatedEvent extends ProjectEvent {

    ObjectMapper mapper = new ObjectMapper();

    public ProjectUpdatedEvent() {
        super();
        this.eventType = "ProjectUpdatedEvent";
    };

    private ProjectUpdatedEvent(UpdateProjectCommand command, Project project) {
        super();
        this.eventType = "ProjectUpdatedEvent";
        this.entityId = project.projectId;
        this.payload = createEventPayload(command, project);
    }

    public static ProjectUpdatedEvent create(UpdateProjectCommand command, Project project){
        return new ProjectUpdatedEvent(command, project);
    }

    private ObjectNode createEventPayload(UpdateProjectCommand command, Project project) {
        ObjectNode root = mapper.createObjectNode();
        return root;
    }

    @JsonIgnore
    public String getProjectId() {
        return this.payload.get(PROJECT_ID).asText();
    }

    @JsonIgnore
    public String getProjectName() {
        return this.payload.get(PROJECT_NAME).asText();
    }

    @JsonIgnore
    public String getProjectDescription() {
        return this.payload.get(PROJECT_DESCRIPTION).asText();
    }

    @JsonIgnore
    public String getProjectDataUseDeclaration() {
        return this.payload.get(PROJECT_DATA_USE_DECLARATION).asText();
    }

    @JsonIgnore
    public LocalDateTime getStartDate() {
        return mapper.convertValue(payload.get(START_DATE),
        new TypeReference<LocalDateTime>() {
        });
    }

    @JsonIgnore
    public LocalDateTime getEndDate() {
        return mapper.convertValue(payload.get(END_DATE),
        new TypeReference<LocalDateTime>() {
        });
    }

    @JsonIgnore
    public List<ProjectMetaData> getMetaDataInfo() {
        return mapper.convertValue(payload.get(META_DATA_INFO),
        new TypeReference<List<ProjectMetaData>>() {
        });
    }

    @JsonIgnore
    public List<Label> getLabels() {
        return mapper.convertValue(payload.get(LABELS),
        new TypeReference<List<Label>>() {
        });
    }

    @Override
    public void accept(ProjectEventVisitor visitor) {
        visitor.visit(this);
    }

}
