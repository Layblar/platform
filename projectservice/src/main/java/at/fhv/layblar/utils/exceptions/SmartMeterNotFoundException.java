package at.fhv.layblar.utils.exceptions;

public class SmartMeterNotFoundException extends ResponseException{

    public SmartMeterNotFoundException() {
        super(404, "Smart Reader not found");
    }

}
