package at.fhv.layblar.utils.exceptions;

public class LabeledDataNotFoundException extends ResponseException {

    public LabeledDataNotFoundException() {
        super(404, "Labeled data not found");
    }
    
}
