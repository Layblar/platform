package at.fhv.layblar.utils.exceptions;

public class DeviceCategoryNotFoundException extends ResponseException {

    public DeviceCategoryNotFoundException() {
        super(404, "DeviceCategory not found");
    }

}
