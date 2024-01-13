package at.fhv.layblar.infrastructure.events;

public class HouseholdDeletedEvent extends HouseholdEvent {

    public HouseholdDeletedEvent() {
        super();
        this.eventType = "HouseholdDeletedEvent";
    };

}
