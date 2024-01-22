package at.fhv.layblar.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;

@Entity
public abstract class LabeledDataEvent extends Event {

    public LabeledDataEvent(){
        super();
        this.entityType = "LabeledData";
    }

    @JsonIgnore
    public Integer getBatchNumber() {
        return this.payload.get("batchNumber").asInt();
    }


    public abstract void accept(LabeledDataEventVisitor visitor);

}
