package at.fhv.layblar.labelServiceRouting.model;

import java.time.LocalDateTime;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceDTO;

public class LabelEventDTO {

    public String labelEventId;
    public String householdId;
    public DeviceDTO device;
    public LocalDateTime timestamp;
    public LabelEventType labelEventType;
    
}
