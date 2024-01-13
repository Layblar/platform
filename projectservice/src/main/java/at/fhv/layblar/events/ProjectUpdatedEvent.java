package at.fhv.layblar.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        root.put(PROJECT_NAME, command.projectName);
        root.put(PROJECT_DESCRIPTION, command.projectName);
        root.put(PROJECT_DATA_USE_DECLARATION, command.projectName);
        root.put(START_DATE, command.startDate.toString());
        root.put(END_DATE, command.endDate.toString());
        root.putPOJO(META_DATA_INFO, command.metaData);
        root.putPOJO(LABELS, command.labels);
        return root;
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
        return LocalDateTime.parse(this.payload.get(START_DATE).asText());
    }

    @JsonIgnore
    public LocalDateTime getEndDate() {
        return LocalDateTime.parse(this.payload.get(END_DATE).asText());
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
