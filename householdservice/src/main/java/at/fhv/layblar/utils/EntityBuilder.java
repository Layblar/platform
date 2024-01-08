package at.fhv.layblar.utils;

import java.util.Collections;
import java.util.List;

import at.fhv.layblar.domain.Household;
import at.fhv.layblar.events.DeviceAddedEvent;
import at.fhv.layblar.events.DeviceRemovedEvent;
import at.fhv.layblar.events.DeviceUpdatedEvent;
import at.fhv.layblar.events.Event;
import at.fhv.layblar.events.EventVisitor;
import at.fhv.layblar.events.HouseholdCreatedEvent;
import at.fhv.layblar.events.HouseholdDeletedEvent;
import at.fhv.layblar.events.HouseholdUserJoinedEvent;
import at.fhv.layblar.events.HouseholdUserLeftEvent;
import at.fhv.layblar.events.SmartMeterRegisteredEvent;
import at.fhv.layblar.events.SmartMeterRemovedEvent;

public class EntityBuilder {
    
    public static Household buildEntity(List<Event> events) {
        Household household = new Household();
        Collections.sort(events);
        for(Event event : events) {
            event.accept(new EventVisitor() {

                @Override
                public void visit(HouseholdCreatedEvent event) {
                    household.apply(event);
                }

                @Override
                public void visit(HouseholdUserJoinedEvent event) {
                    household.apply(event);
                }

                @Override
                public void visit(HouseholdUserLeftEvent event) {
                    household.apply(event);
                }

                @Override
                public void visit(SmartMeterRegisteredEvent event) {
                    household.apply(event);
                }

                @Override
                public void visit(SmartMeterRemovedEvent event) {
                    household.apply(event);
                }

                @Override
                public void visit(DeviceAddedEvent event) {
                    household.apply(event);
                }

                @Override
                public void visit(DeviceUpdatedEvent event) {
                    household.apply(event);
                }

                @Override
                public void visit(DeviceRemovedEvent event) {
                    household.apply(event);
                }

                @Override
                public void visit(HouseholdDeletedEvent event) {
                    household.apply(event);
                }
                
            });
        }
        return household;
    }

}
