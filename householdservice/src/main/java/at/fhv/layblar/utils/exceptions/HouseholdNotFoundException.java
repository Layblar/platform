package at.fhv.layblar.utils.exceptions;

public class HouseholdNotFoundException extends ResponseException {

    public HouseholdNotFoundException(String message) {
        super(404, message);
    }
    
}
