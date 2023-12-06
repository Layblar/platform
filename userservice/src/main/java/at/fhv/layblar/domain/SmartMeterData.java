package at.fhv.layblar.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SmartMeterData {
    @Id
    public String id;

    public SmartMeterData() {}
}
