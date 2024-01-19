package at.fhv.layblar.infrastructure.events;

public class ProjectCreatedEvent extends ProjectEvent {

    public ProjectCreatedEvent() {
        super();
        this.eventType = "ProjectCreatedEvent";
    };

    @Override
    public void accept(ProjectEventVisitor visitor) {
        visitor.visit(this);
    }

}
