package at.fhv.layblar;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeterDataReading {

    public String timestamp;
    public String sensorId;
    @JsonProperty("1.7.0")
    public Float v1_7_0;
    @JsonProperty("1.8.0")
    public Float v1_8_0;
    @JsonProperty("2.7.0")
    public Float v2_7_0;
    @JsonProperty("2.8.0")
    public Float v2_8_0;
    @JsonProperty("3.8.0")
    public Float v3_8_0;
    @JsonProperty("4.8.0")
    public Float v4_8_0;
    @JsonProperty("16.7.0")
    public Float v16_7_0;
    @JsonProperty("31.7.0")
    public Float v31_7_0;
    @JsonProperty("32.7.0")
    public Float v32_7_0;
    @JsonProperty("51.7.0")
    public Float v51_7_0;
    @JsonProperty("52.7.0")
    public Float v52_7_0;
    @JsonProperty("71.7.0")
    public Float v71_7_0;
    @JsonProperty("72.7.0")
    public Float v72_7_0;
    public String uptime;

    public MeterDataReading() {}   
    
}
