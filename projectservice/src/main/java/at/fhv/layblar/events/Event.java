package at.fhv.layblar.events;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Event")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "eventType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProjectCreatedEvent.class, name = "ProjectCreatedEvent"),
        @JsonSubTypes.Type(value = ProjectUpdatedEvent.class, name = "ProjectUpdatedEvent"),
        @JsonSubTypes.Type(value = ProjectJoinedEvent.class, name = "ProjectJoinedEvent"),
        @JsonSubTypes.Type(value = LabeledDataAddedEvent.class, name = "LabeledDataAddedEvent"),
        @JsonSubTypes.Type(value = LabeledDataUpdatedEvent.class, name = "LabeledDataUpdatedEvent"),
        @JsonSubTypes.Type(value = LabeledDataRemovedEvent.class, name = "LabeledDataRemovedEvent"),
})
public abstract class Event extends PanacheEntityBase implements Comparable<Event> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String eventId;
    public String eventType;
    public String entityType;
    public String entityId;
    public LocalDateTime timestamp = LocalDateTime.now();
    @JdbcTypeCode(SqlTypes.JSON)
    public ObjectNode payload;

    @Override
    public int compareTo(Event event) {
        return timestamp.compareTo(event.timestamp);
    }

}
