package at.fhv.layblar.utils.exceptions;

public class ProjectNotFoundException extends ResponseException {

    public ProjectNotFoundException(String message) {
        super(404, message);
    }
    
}
