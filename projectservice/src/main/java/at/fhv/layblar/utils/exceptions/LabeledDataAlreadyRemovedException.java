package at.fhv.layblar.utils.exceptions;

public class LabeledDataAlreadyRemovedException extends ResponseException {

    public LabeledDataAlreadyRemovedException() {
        super(400, "Data was already removed");
    }
    
}
