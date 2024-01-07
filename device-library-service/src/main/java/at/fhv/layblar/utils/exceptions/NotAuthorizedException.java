package at.fhv.layblar.utils.exceptions;

public class NotAuthorizedException extends ResponseException {

    public NotAuthorizedException(String message) {
        super(401, message);
    }
    
}
