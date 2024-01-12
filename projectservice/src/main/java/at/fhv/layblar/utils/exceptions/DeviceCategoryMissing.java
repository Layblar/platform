package at.fhv.layblar.utils.exceptions;

public class DeviceCategoryMissing extends ResponseException {

    public DeviceCategoryMissing() {
        super(400, "Your devices have not the required categories to join the project");
    }
    
}
