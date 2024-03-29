package at.fhv.layblar.infrastructure;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.domain.readmodel.DeviceCategoryReadModel;
import at.fhv.layblar.domain.readmodel.LabelReadModel;
import at.fhv.layblar.domain.readmodel.ProjectReadModel;
import at.fhv.layblar.events.ProjectCreatedEvent;
import at.fhv.layblar.events.ProjectEvent;
import at.fhv.layblar.events.ProjectEventVisitor;
import at.fhv.layblar.events.ProjectJoinedEvent;
import at.fhv.layblar.events.ProjectUpdatedEvent;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProjectEventConsumer {

    @Inject
    ObjectMapper mapper;

    @Incoming("project")
    @Blocking
    @Transactional
    public void process(Record<String,JsonNode> record) {

        try {
            ProjectEvent event = deserializeEvent(record.value());
            Optional<ProjectReadModel> optProject = ProjectReadModel.findByIdOptional(event.entityId);
            ProjectReadModel project = new ProjectReadModel();
            if(optProject.isPresent()){
                project = optProject.get();
            }
            project = handleProjectEvent(project, event);
            saveToDatabase(project);
        } catch (Exception e) {
            //e.printStackTrace();
        }

    }

    private ProjectReadModel handleProjectEvent(ProjectReadModel project, ProjectEvent event) {
        event.accept(new ProjectEventVisitor() {

            @Override
            public void visit(ProjectCreatedEvent event) {
                project.apply(event);    
            }

            @Override
            public void visit(ProjectUpdatedEvent event) {
                project.apply(event);
            }

            @Override
            public void visit(ProjectJoinedEvent event) {
                project.apply(event);
            }
            
        });
        return project;
    }

    private void saveToDatabase(ProjectReadModel project){
//https://stackoverflow.com/questions/39067243/hibernate-duplicate-key-value-violates-unique-constraint-on-collection
        for (LabelReadModel label : project.labels) {
            List<DeviceCategoryReadModel> categories = label.categories.stream().map(
                cat -> DeviceCategoryReadModel.getEntityManager().merge(cat)
            ).collect(Collectors.toList());
            label.categories = categories;
        }
        List<LabelReadModel> labels = project.labels.stream().map(
            lab -> LabelReadModel.getEntityManager().merge(lab)).collect(Collectors.toList());
        project.addLabels(labels);
        project.persist();
    }

    private ProjectEvent deserializeEvent(JsonNode value) throws JsonMappingException, JsonProcessingException {
        ObjectNode root = mapper.createObjectNode();
        root.put("entityId",value.get("after").get("entityid").asText());
        root.put("entityType",value.get("after").get("entitytype").asText());
        root.put("eventType",value.get("after").get("eventtype").asText());
        root.put("eventId",value.get("after").get("eventid").asText());
        // Timestamp is sent as microseconds so it must be divided by 1000 to get milliseconds
        Instant instant = Instant.ofEpochMilli(value.get("after").get("timestamp").asLong() / 1000);
        LocalDateTime timestamp = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        root.put("timestamp", timestamp.toString());
        JsonNode payload = mapper.readTree(value.get("after").get("payload").asText());
        root.set("payload", payload);
        return mapper.treeToValue(root, ProjectEvent.class);
    }
    
}
