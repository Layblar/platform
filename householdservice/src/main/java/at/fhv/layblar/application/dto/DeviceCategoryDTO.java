package at.fhv.layblar.application.dto;

import at.fhv.layblar.domain.DeviceCategory;

public class DeviceCategoryDTO {

    public String deviceCategoryId;
    public String deviceCategoryName;
    public String deviceCategoryDescription;

    private DeviceCategoryDTO(String deviceCategoryId, String deviceCategoryName, String deviceCategoryDescription) {
        this.deviceCategoryId = deviceCategoryId;
        this.deviceCategoryName = deviceCategoryName;
        this.deviceCategoryDescription = deviceCategoryDescription;
    }

    public static DeviceCategoryDTO createDeviceCategoryDTO(DeviceCategory deviceCategory){
        return new DeviceCategoryDTO(deviceCategory.deviceCategoryId, deviceCategory.deviceCategoryName, deviceCategory.deviceCategoryDescription);
    }

    
    
}


