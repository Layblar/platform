package at.fhv.layblar.utils.exceptions;

public class ProjectMetaDataMissingException extends ResponseException {

    public ProjectMetaDataMissingException() {
        super(400, "Missing houeshold meta data for the project");
    }
    
}
