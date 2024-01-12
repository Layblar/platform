package at.fhv.layblar.events;

import jakarta.persistence.Entity;

@Entity
public abstract class LabelEvent extends Event {

    public LabelEvent(){
        super();
        this.entityType = "Label";
    }

}
