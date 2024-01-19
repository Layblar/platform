package at.fhv.layblar.infrastructure.events;

public class ProjectUpdatedEvent extends ProjectEvent {

    public ProjectUpdatedEvent() {
        super();
        this.eventType = "ProjectUpdatedEvent";
    };

    @Override
    public void accept(ProjectEventVisitor visitor) {
        visitor.visit(this);
    }

}
