package at.fhv.layblar.infrastructure.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.domain.DeviceCategory;
import at.fhv.layblar.domain.Label;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "eventType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProjectCreatedEvent.class, name = "ProjectCreatedEvent"),
        @JsonSubTypes.Type(value = ProjectUpdatedEvent.class, name = "ProjectUpdatedEvent")
})
public abstract class ProjectEvent {

    static final String PROJECT_ID = "projectId";
    static final String PROJECT_NAME = "projectName";
    static final String PROJECT_DESCRIPTION = "projectDescription";
    static final String PROJECT_DATA_USE_DECLARATION = "projectDataUseDeclaration";
    static final String START_DATE = "startDate";
    static final String END_DATE = "endDate";
    static final String META_DATA_INFO = "metaDataInfo";
    static final String LABELS = "labels";
    static final String CREATED_AT = "createdAt";
    static final String RESEARCHER = "researcher";
    static final String PARTICIPANTS = "participants";

    private ObjectMapper mapper = new ObjectMapper();

    public String eventId;
    public String eventType;
    public String entityType;
    public String entityId;
    public LocalDateTime timestamp;
    public ObjectNode payload;

    public abstract void accept(ProjectEventVisitor visitor);
    
    public ProjectEvent(){
        super();
        this.entityType = "Project";
    }

    @JsonIgnore
    public List<DeviceCategory> getDeviceCategories() {
        List<Label> labels = mapper.convertValue(payload.get(LABELS),
        new TypeReference<List<Label>>() {
        });
        return labels.stream().flatMap(label -> label.categories.stream()).collect(Collectors.toList());
    }

}
