package at.fhv.layblar.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import at.fhv.layblar.commands.AddLabeledDataCommand;
import at.fhv.layblar.commands.RemoveLabeledDataCommand;
import at.fhv.layblar.commands.UpdateLabeledDataCommand;
import at.fhv.layblar.events.LabeledDataAddedEvent;
import at.fhv.layblar.events.LabeledDataRemovedEvent;
import at.fhv.layblar.events.LabeledDataUpdatedEvent;
import at.fhv.layblar.utils.exceptions.LabeledDataAlreadyRemovedException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LabeledData extends PanacheEntityBase {

    @Id
    public String labeledDataId;
    public String householdId;
    @JdbcTypeCode(SqlTypes.JSON)
    public Device device;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<SmartMeterData> smartMeterData;
    public LocalDateTime createdAt;
    public Boolean isRemoved;
    public Boolean isHumanLabeled;

    public LabeledDataAddedEvent process(AddLabeledDataCommand command) {
        return LabeledDataAddedEvent.create(command);
    }

    public LabeledDataUpdatedEvent process(UpdateLabeledDataCommand command) throws LabeledDataAlreadyRemovedException {
        if(isRemoved){
            throw new LabeledDataAlreadyRemovedException();
        }
        return LabeledDataUpdatedEvent.create(command, this);
    }

    public LabeledDataRemovedEvent process(RemoveLabeledDataCommand command) {
        return LabeledDataRemovedEvent.create(command, this);
    }


    public void apply(LabeledDataAddedEvent event) {
        this.labeledDataId = event.getLabeledDataId();
        this.device = event.getDevice();
        this.householdId = event.getHouseholdId();
        this.smartMeterData = event.getSmartMeterData();
        this.createdAt = event.getCreatedAt();
        this.isRemoved = false;
        this.isHumanLabeled = true;
    }
    public void apply(LabeledDataUpdatedEvent event) {
        this.device = event.getDevice();
        this.smartMeterData = event.getSmartMeterData();
    }
    public void apply(LabeledDataRemovedEvent event) {
        this.isRemoved = true;
    }

    
}
