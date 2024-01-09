package at.fhv.layblar.projectServiceRouting.model;

import java.util.List;

import at.fhv.layblar.deviceLibraryServiceRouting.model.DeviceCategoryDTO;

public class LabelDTO {

    public String labelId;
    public String labelName;
    public String labelDescription;
    public String labelMethod;
    public List<DeviceCategoryDTO> categories;

}
