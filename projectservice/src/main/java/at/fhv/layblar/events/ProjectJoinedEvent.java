package at.fhv.layblar.events;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.application.dto.ProjectMetaDataDTO;
import at.fhv.layblar.commands.JoinProjectCommand;
import at.fhv.layblar.domain.model.Project;
import at.fhv.layblar.domain.model.ProjectMetaData;
import jakarta.persistence.Entity;

@Entity
public class ProjectJoinedEvent extends ProjectEvent {

    public ProjectJoinedEvent() {
        super();
        this.eventType = "ProjectJoinedEvent";
    };

    public ProjectJoinedEvent(JoinProjectCommand command, Project project) {
        super();
        this.eventType = "ProjectJoinedEvent";
        this.entityId = project.projectId;
        this.payload = createEventPayload(command, project);
    }

    @JsonIgnore
    public String getHouseholdId() {
        return this.payload.get("householdId").asText();
    }

    @JsonIgnore
    public List<ProjectMetaData> getHouseholdMetaData() {
        return new ObjectMapper().convertValue(payload.get("householdMetaData"),
        new TypeReference<List<ProjectMetaData>>() {
        });
    }

    private ObjectNode createEventPayload(JoinProjectCommand command, Project project) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("householdId", command.householdId);
        ArrayNode metaData = root.putArray("householdMetaData");
        for (ProjectMetaDataDTO metaDataDTO : command.metaData) {
            metaData.addObject()
                .put("metaDataId", metaDataDTO.metaDataId)
                .put("metaDataName", metaDataDTO.metaDataName)
                .put("type", metaDataDTO.type)
                .put("value", metaDataDTO.value);
        }
        return root;
    }

    @Override
    public void accept(ProjectEventVisitor visitor) {
        visitor.visit(this);
    }

    public static ProjectJoinedEvent create(JoinProjectCommand command, Project project) {
        return new ProjectJoinedEvent(command, project);
    }

}
