package at.fhv.layblar.infrastructure.events;

public interface ProjectEventVisitor {

    void visit(ProjectCreatedEvent event);

    void visit(ProjectUpdatedEvent event);

}
