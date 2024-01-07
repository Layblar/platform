package at.fhv.layblar.utils.exceptions;

public class DeviceNotFoundException extends ResponseException {

    public DeviceNotFoundException() {
        super(404, "Device not found");
    }

}
