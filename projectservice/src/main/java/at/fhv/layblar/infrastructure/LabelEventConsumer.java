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

import at.fhv.layblar.domain.model.Label;
import at.fhv.layblar.domain.model.LabeledData;
import at.fhv.layblar.domain.model.Project;
import at.fhv.layblar.domain.model.ProjectParticipant;
import at.fhv.layblar.domain.readmodel.ProjectLabeledData;
import at.fhv.layblar.events.LabeledDataAddedEvent;
import at.fhv.layblar.events.LabeledDataEvent;
import at.fhv.layblar.events.LabeledDataEventVisitor;
import at.fhv.layblar.events.LabeledDataRemovedEvent;
import at.fhv.layblar.events.LabeledDataUpdatedEvent;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LabelEventConsumer {

    @Inject
    ObjectMapper mapper;

    @Incoming("label")
    @Blocking
    @Transactional
    public void process(Record<String,JsonNode> record) {
        try {
            LabeledDataEvent event = deserializeEvent(record.value());
            Optional<LabeledData> optLabeledData = LabeledData.findByIdOptional(event.entityId);
            LabeledData labeledData = new LabeledData();
            if(optLabeledData.isPresent()){
                labeledData = optLabeledData.get();
            }
            labeledData.labeledDataId = event.entityId;
            labeledData = handleLabeledDataEvent(labeledData, event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private LabeledData handleLabeledDataEvent(LabeledData labeledData, LabeledDataEvent event) {
        event.accept(new LabeledDataEventVisitor() {

            @Override
            public void visit(LabeledDataAddedEvent event) {
                labeledData.apply(event);
                labeledData.persist();
                addLabeledDataToProjects(labeledData);
            }

            @Override
            public void visit(LabeledDataUpdatedEvent event) {
                labeledData.apply(event);
                labeledData.persist();
            }

            @Override
            public void visit(LabeledDataRemovedEvent event) {
                labeledData.apply(event);
                labeledData.delete();
            }
            
        });
        return labeledData;
    }

    private void addLabeledDataToProjects(LabeledData labeledData) {
        List<Project> projects = Project.list("participants.householdId", labeledData.householdId);
        for (Project project : projects) {
            if(project.isActive()){
                List<String> deviceCategoryIds = labeledData.device.deviceCategory.stream()
                    .map(category -> category.deviceCategoryId)
                    .collect(Collectors.toList());
                Label label = project.labels.stream()
                    .filter(l -> l.categories.stream().anyMatch(category -> deviceCategoryIds.contains(category.deviceCategoryId)))
                    .findFirst()
                    .orElse(null);
                Optional<ProjectParticipant> optPart = project.participants.stream().filter(par -> par.householdId.equals(labeledData.householdId)).findFirst();
                if(label != null && optPart.isPresent()) {
                    ProjectLabeledData projectLabeledData = ProjectLabeledData.create(labeledData, optPart.get().householdMetaData, label.labelId, project);
                    projectLabeledData.persist();
                }

            }
        }
    }

    private LabeledDataEvent deserializeEvent(JsonNode value) throws JsonMappingException, JsonProcessingException{
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
        return mapper.treeToValue(root, LabeledDataEvent.class);
    }
    
}
