package at.fhv.layblar.utils.exceptions;

public class LabelCategoryConflictException extends ResponseException {

    public LabelCategoryConflictException() {
        super(400, "Two or more Labels have the same Category assigned");
    }
    
}
