package at.fhv.layblar.events;

import jakarta.persistence.Entity;

@Entity
public abstract class LabeledDataEvent extends Event {

    public LabeledDataEvent(){
        super();
        this.entityType = "LabeledData";
    }

    public abstract void accept(LabeledDataEventVisitor visitor);

}
