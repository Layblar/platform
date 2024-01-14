package at.fhv.layblar.events;

public interface LabeledDataEventVisitor {

    void visit(LabeledDataAddedEvent event);

    void visit(LabeledDataUpdatedEvent event);

    void visit(LabeledDataRemovedEvent event);

}
