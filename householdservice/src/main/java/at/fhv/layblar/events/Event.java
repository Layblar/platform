package at.fhv.layblar.events;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Event")
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
