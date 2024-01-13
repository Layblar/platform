package at.fhv.layblar.application.dto;

import at.fhv.layblar.domain.model.DeviceCategory;

public class DeviceCategoryDTO {

    public String deviceCategoryId;
    public String deviceCategoryName;
    public String deviceCategoryDescription;

    public DeviceCategoryDTO(){}

    private DeviceCategoryDTO(String deviceCategoryId, String deviceCategoryName, String deviceCategoryDescription) {
        this.deviceCategoryId = deviceCategoryId;
        this.deviceCategoryName = deviceCategoryName;
        this.deviceCategoryDescription = deviceCategoryDescription;
    }

    public static DeviceCategoryDTO createDeviceCategoryDTO(DeviceCategory category){
        return new DeviceCategoryDTO(category.deviceCategoryId, category.deviceCategoryName, category.deviceCategoryDescription);
    }

    

}


