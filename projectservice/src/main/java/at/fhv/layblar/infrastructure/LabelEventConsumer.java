package at.fhv.layblar.infrastructure;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import at.fhv.layblar.domain.readmodel.ViableProject;
import at.fhv.layblar.domain.readmodel.LabeledDataReadModel;
import at.fhv.layblar.domain.readmodel.LabeledDataReadModelKey;
import at.fhv.layblar.domain.readmodel.ProjectLabeledData;
import at.fhv.layblar.domain.readmodel.ProjectReadModel;
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

    @Incoming("project")
    @Blocking
    @Transactional
    public void process(Record<String, JsonNode> record) {
        try {
            LabeledDataEvent event = deserializeEvent(record.value());
            Optional<LabeledDataReadModel> optLabeledData = LabeledDataReadModel.findByIdOptional(new LabeledDataReadModelKey(event.entityId, event.getBatchNumber()));
            LabeledDataReadModel labeledData = new LabeledDataReadModel();
            if (optLabeledData.isPresent()) {
                labeledData = optLabeledData.get();
            }
            labeledData = handleLabeledDataEvent(labeledData, event);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    private LabeledDataReadModel handleLabeledDataEvent(LabeledDataReadModel labeledData, LabeledDataEvent event) {
        event.accept(new LabeledDataEventVisitor() {

            @Override
            public void visit(LabeledDataAddedEvent event) {
                labeledData.apply(event);
                labeledData.persist();
                updateValidToDates(labeledData, event);
                updateValidFromDates(labeledData, event);
            }

            @Override
            public void visit(LabeledDataUpdatedEvent event) {
                labeledData.apply(event);
                labeledData.persist();
                updateValidToDates(labeledData, event);
                updateValidFromDates(labeledData, event);
            }

            @Override
            public void visit(LabeledDataRemovedEvent event) {
                labeledData.apply(event);
                labeledData.delete();
                updateValidToDates(labeledData, event);
            }

        });
        return labeledData;
    }

    private void updateValidFromDates(LabeledDataReadModel labeledData, LabeledDataEvent event) {
        List<String> deviceCategoryIds = labeledData.device.categories.stream()
                .map(category -> category.deviceCategoryId)
                .collect(Collectors.toList());
        List<ViableProject> viableProjects = ProjectReadModel.findProjectWithMatchingLabels(labeledData.householdId,
                deviceCategoryIds);
        List<ProjectLabeledData> data = new LinkedList<>();
        for (ViableProject viableProject : viableProjects) {
            data.addAll(ProjectLabeledData.create(labeledData, viableProject, event));
        }
        ProjectLabeledData.persist(data);
    }

    private void updateValidToDates(LabeledDataReadModel labeledData, LabeledDataEvent event) {
        // if the data was already invalidated in the past do not overwrite validTo date
        // batchNumber >= currentBatchNumber -> if that is removed and a batchNumber does not exists
        // anymore the old data must also be updated
        List<ProjectLabeledData> data = ProjectLabeledData.list(
                "labeledDataId = ?1 AND batchNumber >= ?2 AND validTo >= ?3 order by validTo desc", labeledData.labeledDataId,
                event.getBatchNumber(), event.timestamp);
        ProjectLabeledData.persist(data.stream().map(pld -> {
            pld.validTo = event.timestamp;
            return pld;
        }).collect(Collectors.toList()));
    }

    private LabeledDataEvent deserializeEvent(JsonNode value) throws JsonMappingException, JsonProcessingException {
        ObjectNode root = mapper.createObjectNode();
        root.put("entityId", value.get("after").get("entityid").asText());
        root.put("entityType", value.get("after").get("entitytype").asText());
        root.put("eventType", value.get("after").get("eventtype").asText());
        root.put("eventId", value.get("after").get("eventid").asText());
        // Timestamp is sent as microseconds so it must be divided by 1000 to get
        // milliseconds
        Instant instant = Instant.ofEpochMilli(value.get("after").get("timestamp").asLong() / 1000);
        LocalDateTime timestamp = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        root.put("timestamp", timestamp.toString());
        JsonNode payload = mapper.readTree(value.get("after").get("payload").asText());
        root.set("payload", payload);
        return mapper.treeToValue(root, LabeledDataEvent.class);
    }

}
