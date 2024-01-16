package at.fhv.layblar.application.dto;

import java.time.LocalDateTime;

public class LabelEventDTO {

    public String labelEventId;
    public String householdId;
    public String projectId;
    public String labelId;
    public String deviceId;
    public LocalDateTime timestamp;
    public LabelEventType labelEventType;
    
}
