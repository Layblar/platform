package at.fhv.layblar.utils.exceptions;

public class LabelNotFoundException extends ResponseException {

    public LabelNotFoundException() {
        super(404, "Label not found");
    }

}
