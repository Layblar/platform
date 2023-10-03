package at.fhv.layblar;

import com.fasterxml.jackson.databind.JsonNode;

public class MeterDataReading {

    public String topic;
    public String time;
    public JsonNode message;

    public MeterDataReading() {}

    public MeterDataReading(String topic, String time, JsonNode message) {
        this.topic = topic;
        this.time = time;
        this.message = message;
    }
    
}
