package at.fhv.layblar.utils.exceptions;

public class ProjectAlreadyJoinedException extends ResponseException {

    public ProjectAlreadyJoinedException() {
        super(400, "Already part of the project");
    }
    
}
