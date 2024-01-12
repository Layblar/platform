package at.fhv.layblar.utils.exceptions;

public class SmartMeterAlreadyRegisteredException extends ResponseException {

    public SmartMeterAlreadyRegisteredException() {
        super(400, "Smart Reader is already registered");
    }

}
