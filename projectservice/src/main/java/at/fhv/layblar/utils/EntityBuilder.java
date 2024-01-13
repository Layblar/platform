package at.fhv.layblar.utils;

import java.util.Collections;
import java.util.List;

import at.fhv.layblar.events.ProjectEventVisitor;
import at.fhv.layblar.events.ProjectJoinedEvent;
import at.fhv.layblar.domain.model.Project;
import at.fhv.layblar.events.ProjectCreatedEvent;
import at.fhv.layblar.events.ProjectEvent;
import at.fhv.layblar.events.ProjectUpdatedEvent;

public class EntityBuilder {
    
    public static Project buildEntity(List<ProjectEvent> events) {
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

}
