package at.fhv.layblar.householdServiceRouting.model;

import java.util.List;

public class HouseholdDTO {

    public String householdId;
    public List<HouseholdUserDTO> users;
    public List<HouseholdDeviceDTO> devices;
    public List<SmartMeterDTO> smartMeters;

}
