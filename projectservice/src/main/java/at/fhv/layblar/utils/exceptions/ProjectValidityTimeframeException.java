package at.fhv.layblar.utils.exceptions;

public class ProjectValidityTimeframeException extends ResponseException {

    public ProjectValidityTimeframeException(String message) {
        super(400, message);
    }

}
