package at.fhv.layblar.domain.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import at.fhv.layblar.commands.AddLabeledDataCommand;
import at.fhv.layblar.commands.RemoveLabeledDataCommand;
import at.fhv.layblar.commands.UpdateLabeledDataCommand;
import at.fhv.layblar.events.LabeledDataAddedEvent;
import at.fhv.layblar.events.LabeledDataRemovedEvent;
import at.fhv.layblar.events.LabeledDataUpdatedEvent;
import at.fhv.layblar.utils.exceptions.LabeledDataAlreadyRemovedException;

public class LabeledData {

    private static final Integer BATCH_SIZE = 1000;

    public String labeledDataId;
    public String householdId;
    public Device device;
    public List<SmartMeterData> smartMeterData = new LinkedList<>();
    public LocalDateTime createdAt;
    public Boolean isRemoved;
    public Boolean isHumanLabeled;

    public List<LabeledDataAddedEvent> process(AddLabeledDataCommand command) {
        String labeledDataId = UUID.randomUUID().toString();
        List<LabeledDataAddedEvent> events = new LinkedList<>();
        int totalSize = command.smartMeterData.size();
        int batchNumber = 0;
        for (int start = 0; start < totalSize; start+=BATCH_SIZE) {
            int end = Math.min(start + BATCH_SIZE, totalSize);
            batchNumber++;
            events.add(LabeledDataAddedEvent.create(labeledDataId, command, command.smartMeterData.subList(start, end), batchNumber));
        }
        return events;
    }

    public List<LabeledDataUpdatedEvent> process(UpdateLabeledDataCommand command) throws LabeledDataAlreadyRemovedException {
        if(isRemoved){
            throw new LabeledDataAlreadyRemovedException();
        }
        List<LabeledDataUpdatedEvent> events = new LinkedList<>();
        int totalSize = command.smartMeterData.size();
        int batchNumber = 0;
        for (int start = 0; start < totalSize; start+=BATCH_SIZE) {
            int end = Math.min(start + BATCH_SIZE, totalSize);
            batchNumber++;
            events.add(LabeledDataUpdatedEvent.create(command, this, command.smartMeterData.subList(start, end), batchNumber));
        }
        return events;
    }

    public LabeledDataRemovedEvent process(RemoveLabeledDataCommand command) {
        return LabeledDataRemovedEvent.create(command, this);
    }


    public void apply(LabeledDataAddedEvent event) {
        this.labeledDataId = event.getLabeledDataId();
        this.device = event.getDevice();
        this.householdId = event.getHouseholdId();
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

    public void apply(List<LabeledDataAddedEvent> events) {
        for (LabeledDataAddedEvent event : events) {
            this.apply(event);
        }
    }

    public void applyUpdatedList(List<LabeledDataUpdatedEvent> events) {
        for (LabeledDataUpdatedEvent event : events) {
            this.apply(event);
        }
    }

    
}
