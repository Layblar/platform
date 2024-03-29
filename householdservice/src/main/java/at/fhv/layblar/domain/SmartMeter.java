package at.fhv.layblar.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SmartMeter extends PanacheEntityBase {

    @Id
    public String smartMeterId;

    public SmartMeter(){}

    private SmartMeter(String smartMeterId) {
        this.smartMeterId = smartMeterId;
    }

    public static SmartMeter create(String smartMeterId){
        return new SmartMeter(smartMeterId);
    }

}
