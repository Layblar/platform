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
        @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DeviceAddedEvent"),
        @JsonSubTypes.Type(value = DeviceUpdatedEvent.class, name = "DeviceUpdatedEvent"),
        @JsonSubTypes.Type(value = DeviceRemovedEvent.class, name = "DeviceRemovedEvent"),
        @JsonSubTypes.Type(value = HouseholdCreatedEvent.class, name = "HouseholdCreatedEvent"),
        @JsonSubTypes.Type(value = HouseholdUserJoinedEvent.class, name = "HouseholdJoinedEvent"),
        @JsonSubTypes.Type(value = SmartMeterRegisteredEvent.class, name = "SmartMeterRegisteredEvent"),
        @JsonSubTypes.Type(value = SmartMeterRemovedEvent.class, name = "SmartMeterRemovedEvent")
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

    public abstract void accept(EventVisitor visitor);

    @Override
    public int compareTo(Event event) {
        return timestamp.compareTo(event.timestamp);
    }

}
