package at.fhv.layblar.utils;

import java.util.Collections;
import java.util.List;

import at.fhv.layblar.events.ProjectEventVisitor;
import at.fhv.layblar.events.ProjectJoinedEvent;
import at.fhv.layblar.domain.model.LabeledData;
import at.fhv.layblar.domain.model.Project;
import at.fhv.layblar.events.LabeledDataAddedEvent;
import at.fhv.layblar.events.LabeledDataEvent;
import at.fhv.layblar.events.LabeledDataEventVisitor;
import at.fhv.layblar.events.LabeledDataRemovedEvent;
import at.fhv.layblar.events.LabeledDataUpdatedEvent;
import at.fhv.layblar.events.ProjectCreatedEvent;
import at.fhv.layblar.events.ProjectEvent;
import at.fhv.layblar.events.ProjectUpdatedEvent;

public class EntityBuilder {
    
    public static Project buildProjectEntity(List<ProjectEvent> events) {
        Project project = new Project();
        Collections.sort(events);
        for(ProjectEvent event : events) {
            event.accept(new ProjectEventVisitor() {

                @Override
                public void visit(ProjectCreatedEvent event) {
                    project.apply(event);
                }

                @Override
                public void visit(ProjectUpdatedEvent event) {
                    project.apply(event);
                }

                @Override
                public void visit(ProjectJoinedEvent event) {
                    project.apply(event);
                }
                
            });
        }
        return project;
    }

    public static LabeledData buildLabeledDataEntity(List<LabeledDataEvent> events) {
        LabeledData labeledData = new LabeledData();
        Collections.sort(events);
        for(LabeledDataEvent event : events) {
            event.accept(new LabeledDataEventVisitor() {

                @Override
                public void visit(LabeledDataAddedEvent event) {
                    labeledData.apply(event);
                }

                @Override
                public void visit(LabeledDataUpdatedEvent event) {
                    labeledData.apply(event);
                }

                @Override
                public void visit(LabeledDataRemovedEvent event) {
                    labeledData.apply(event);
                }
                
            });
        }
        return labeledData;
    }

}
