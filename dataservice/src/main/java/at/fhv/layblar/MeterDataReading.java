package at.fhv.layblar;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class MeterDataReading extends PanacheEntity  {

    public LocalDateTime time;
    public String sensorId;
    public Float v1_7_0;
    public Float v1_8_0;
    public Float v2_7_0;
    public Float v2_8_0;
    public Float v3_8_0;
    public Float v4_8_0;
    public Float v16_7_0;
    public Float v31_7_0;
    public Float v32_7_0;
    public Float v51_7_0;
    public Float v52_7_0;
    public Float v71_7_0;
    public Float v72_7_0;
    public LocalTime uptime;

    public MeterDataReading() {}

    public MeterDataReading(LocalDateTime time, String sensorId, Float v1_7_0, Float v1_8_0, Float v2_7_0, Float v2_8_0,
            Float v3_8_0, Float v4_8_0, Float v16_7_0, Float v31_7_0, Float v32_7_0, Float v51_7_0, Float v52_7_0,
            Float v71_7_0, Float v72_7_0, LocalTime uptime) {
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
        this.uptime = uptime;
    }
    
}
