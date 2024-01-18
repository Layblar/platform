package at.fhv.layblar.events;

import jakarta.persistence.Entity;

@Entity
public abstract class HouseholdEvent extends Event {
    
    public HouseholdEvent(){
        super();
        this.entityType = "Household";
    }
}
