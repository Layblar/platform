package at.fhv.layblar;

import io.vertx.core.json.JsonObject;
import jakarta.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class MeterDataReading extends PanacheEntity  {

    public String topic;
    public String time;
    public String message;

    public MeterDataReading() {}

    public MeterDataReading(String topic, String time, String message) {
        this.topic = topic;
        this.time = time;
        this.message = message;
    }
    
}
