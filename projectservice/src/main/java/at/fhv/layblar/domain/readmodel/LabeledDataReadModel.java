package at.fhv.layblar.domain.readmodel;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import at.fhv.layblar.domain.model.Device;
import at.fhv.layblar.domain.model.SmartMeterData;
import at.fhv.layblar.events.LabeledDataAddedEvent;
import at.fhv.layblar.events.LabeledDataRemovedEvent;
import at.fhv.layblar.events.LabeledDataUpdatedEvent;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(LabeledDataReadModelKey.class)
public class LabeledDataReadModel extends PanacheEntityBase {

    @Id
    public String labeledDataId;
    @Id
    public Integer batchNumber;
    public String householdId;
    @JdbcTypeCode(SqlTypes.JSON)
    public Device device;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<SmartMeterData> smartMeterData = new LinkedList<>();
    public LocalDateTime createdAt;
    public Boolean isRemoved;
    public Boolean isHumanLabeled;

    public void apply(LabeledDataAddedEvent event) {
        this.labeledDataId = event.getLabeledDataId();
        this.batchNumber = event.getBatchNumber();
        this.device = event.getDevice();
        this.householdId = event.getHouseholdId();
        this.smartMeterData.removeAll(event.getSmartMeterData());
        this.smartMeterData.addAll(event.getSmartMeterData());
        this.createdAt = event.getCreatedAt();
        this.isRemoved = false;
        this.isHumanLabeled = true;
    }
    public void apply(LabeledDataUpdatedEvent event) {
        this.device = event.getDevice();
        this.smartMeterData.removeAll(event.getSmartMeterData());
        this.smartMeterData.addAll(event.getSmartMeterData());
    }
    public void apply(LabeledDataRemovedEvent event) {
        this.isRemoved = true;
    }

    
}
