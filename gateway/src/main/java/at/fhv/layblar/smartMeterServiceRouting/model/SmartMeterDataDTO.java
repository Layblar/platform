package at.fhv.layblar.smartMeterServiceRouting.model;

import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

@Schema(description = "Smart Meter Data")
public class SmartMeterDataDTO {

    @Schema(name = "time", description = "Time at which Smart-Meter data was recored", example = "2023-05-06T11:15:40")
    public LocalDateTime time;
    @Schema(name = "sensorId", description = "Id of the Smart-Meter that recored the meassurement", example= "123MF34FBDKAS")
    public String sensorId;
    @JsonProperty("1.7.0")
    @Schema(description = "This is the current positive active power in watts measured from Phase A.")
    public Float v1_7_0;
    @JsonProperty("1.8.0")
    @Schema(description = "This is the total positive active energy in watt-hours consumed or generated from Phase A.")
    public Float v1_8_0;
    @JsonProperty("2.7.0")
    @Schema(description = "This is the current negative active power in watts measured from Phase A. This could indicate generated energy.")
    public Float v2_7_0;
    @JsonProperty("2.8.0")
    @Schema(description = "This is the total negative active energy in watt-hours consumed or generated from Phase A.")
    public Float v2_8_0;
    @JsonProperty("3.8.0")
    @Schema(description = "This is the total positive reactive energy in var-hours consumed or generated from Phase A.")
    public Float v3_8_0;
    @JsonProperty("4.8.0")
    @Schema(description = "This is the total negative reactive energy in var-hours consumed or generated from Phase A")
    public Float v4_8_0;
    @JsonProperty("16.7.0")
    @Schema(description = "This is the difference between positive and negative active instantaneous power in watts from Phase A.")
    public Float v16_7_0;
    @JsonProperty("31.7.0")
    @Schema(description = "This is the current in amperes flowing in Phase L1.")
    public Float v31_7_0;
    @JsonProperty("32.7.0")
    @Schema(description = "This is the instantaneous voltage in volts in Phase L1.")
    public Float v32_7_0;
    @JsonProperty("51.7.0")
    @Schema(description = "This is the current in amperes flowing in Phase L2.")
    public Float v51_7_0;
    @JsonProperty("52.7.0")
    @Schema(description = "This is the instantaneous voltage in volts in Phase L2.")
    public Float v52_7_0;
    @JsonProperty("71.7.0")
    @Schema(description = "This is the current in amperes flowing in Phase L3.")
    public Float v71_7_0;
    @JsonProperty("72.7.0")
    @Schema(description = "This is the instantaneous voltage in volts in Phase L3.")
    public Float v72_7_0;

}
