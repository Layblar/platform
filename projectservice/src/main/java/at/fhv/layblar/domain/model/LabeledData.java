package at.fhv.layblar.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import at.fhv.layblar.commands.AddLabeledDataCommand;
import at.fhv.layblar.events.LabeledDataAddedEvent;
import at.fhv.layblar.events.LabeledDataRemovedEvent;
import at.fhv.layblar.events.LabeledDataUpdatedEvent;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LabeledData extends PanacheEntityBase {

    @Id
    public String labeledDataId;
    public String labelId;
    public String householdId;
    public String projectId;
    public String deviceId;
    public List<SmartMeterData> smartMeterData;
    public LocalDateTime createdAt;

    public LabeledDataAddedEvent process(AddLabeledDataCommand command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'process'");
    }
    public void apply(LabeledDataAddedEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apply'");
    }
    public void apply(LabeledDataUpdatedEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apply'");
    }
    public void apply(LabeledDataRemovedEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apply'");
    }
    
}
