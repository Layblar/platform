package at.fhv.layblar.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MeterDataReadingKey implements Serializable {

    public LocalDateTime time;
    public String sensorId;
    public String householdId;
    
}
