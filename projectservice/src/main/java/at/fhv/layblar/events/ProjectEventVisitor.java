package at.fhv.layblar.events;

public interface ProjectEventVisitor {

    void visit(ProjectCreatedEvent event);

    void visit(ProjectUpdatedEvent event);

    void visit(ProjectJoinedEvent event);

}
