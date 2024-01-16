package at.fhv.layblar.application.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.fhv.layblar.domain.model.SmartMeterData;

public class SmartMeterDataDTO {

    public LocalDateTime time;
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

    public SmartMeterDataDTO() {
    }

    private SmartMeterDataDTO(LocalDateTime time, String sensorId, Float v1_7_0, Float v1_8_0, Float v2_7_0,
            Float v2_8_0, Float v3_8_0, Float v4_8_0, Float v16_7_0, Float v31_7_0, Float v32_7_0, Float v51_7_0,
            Float v52_7_0, Float v71_7_0, Float v72_7_0) {
        this.time = time;
        this.sensorId = sensorId;
        this.v1_7_0 = v1_7_0;
        this.v1_8_0 = v1_8_0;
        this.v2_7_0 = v2_7_0;
        this.v2_8_0 = v2_8_0;
        this.v3_8_0 = v3_8_0;
        this.v4_8_0 = v4_8_0;
        this.v16_7_0 = v16_7_0;
        this.v31_7_0 = v31_7_0;
        this.v32_7_0 = v32_7_0;
        this.v51_7_0 = v51_7_0;
        this.v52_7_0 = v52_7_0;
        this.v71_7_0 = v71_7_0;
        this.v72_7_0 = v72_7_0;
    }

    public static SmartMeterDataDTO createSmartMeterDataDTO(SmartMeterData smartMeterData) {
        return new SmartMeterDataDTO(LocalDateTime.parse(smartMeterData.time), smartMeterData.sensorId,
                smartMeterData.v1_7_0, smartMeterData.v1_8_0, smartMeterData.v2_7_0, smartMeterData.v2_8_0,
                smartMeterData.v3_8_0, smartMeterData.v4_8_0, smartMeterData.v16_7_0, smartMeterData.v31_7_0,
                smartMeterData.v32_7_0, smartMeterData.v51_7_0, smartMeterData.v52_7_0, smartMeterData.v71_7_0,
                smartMeterData.v72_7_0);
    }

}
