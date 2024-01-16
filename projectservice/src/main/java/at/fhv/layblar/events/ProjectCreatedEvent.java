package at.fhv.layblar.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.commands.CreateProjectCommand;
import at.fhv.layblar.domain.model.Label;
import at.fhv.layblar.domain.model.ProjectMetaData;
import at.fhv.layblar.domain.model.ProjectParticipant;
import at.fhv.layblar.domain.model.Researcher;
import jakarta.persistence.Entity;

@Entity
public class ProjectCreatedEvent extends ProjectEvent {

    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    public ProjectCreatedEvent() {
        super();
        this.eventType = "ProjectCreatedEvent";
    };

    private ProjectCreatedEvent(CreateProjectCommand command, Researcher researcher) {
        super();
        this.eventType = "ProjectCreatedEvent";
        this.entityId = UUID.randomUUID().toString();
        this.payload = createEventPayload(command, researcher);
    }

    public static ProjectCreatedEvent create(CreateProjectCommand command, Researcher researcher) {
        return new ProjectCreatedEvent(command, researcher);
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

    @JsonIgnore
    public LocalDateTime getCreatedAt() {
        return LocalDateTime.parse(this.payload.get(CREATED_AT).asText());
    }

    @JsonIgnore
    public Researcher getResearcher() {
        return mapper.convertValue(payload.get(RESEARCHER),
        new TypeReference<Researcher>() {
        });
    }

    @JsonIgnore
    public List<ProjectParticipant> getParticipants() {
        return mapper.convertValue(payload.get(PARTICIPANTS),
        new TypeReference<List<ProjectParticipant>>() {
        });
    }

    private ObjectNode createEventPayload(CreateProjectCommand command, Researcher researcher) {
        ObjectNode root = mapper.createObjectNode();
        root.put(PROJECT_ID, entityId);
        root.put(PROJECT_NAME, command.projectName);
        root.put(PROJECT_DESCRIPTION, command.projectDescription);
        root.put(PROJECT_DATA_USE_DECLARATION, command.projectDataUseDeclartion);
        root.put(START_DATE, command.startDate.toString());
        root.put(END_DATE, command.endDate.toString());
        root.put(CREATED_AT, LocalDateTime.now().toString());
        root.putPOJO(RESEARCHER, researcher);
        root.putPOJO(META_DATA_INFO, command.metaData);
        root.putPOJO(LABELS, command.labels);
        return root;
    }

    @Override
    public void accept(ProjectEventVisitor visitor) {
        visitor.visit(this);
    }

}
