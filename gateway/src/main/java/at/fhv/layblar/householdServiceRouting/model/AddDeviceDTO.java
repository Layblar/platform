package at.fhv.layblar.householdServiceRouting.model;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceCategoryDTO;

@Schema(description = "Add Device to Household")
public class AddDeviceDTO {
    
    @Schema(name = "deviceId", description = "If not provided a new deviceId will be created", required = false)
    public String deviceId;
    @Schema(name = "deviceName", description = "Name of the device", required = true)
    public String deviceName;
    public String deviceDescription;
    public String manufacturer;
    public String modelNumber;
    public Integer powerDraw;
    public String energyEfficiencyRating;
    public Float weight;
    @Schema(name = "categories", description = "At least one category must be provided", required = true)
    public List<DeviceCategoryDTO> categories;
}
