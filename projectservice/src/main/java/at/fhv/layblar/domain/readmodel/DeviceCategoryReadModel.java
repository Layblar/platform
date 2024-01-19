package at.fhv.layblar.domain.readmodel;

import at.fhv.layblar.domain.model.DeviceCategory;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DeviceCategoryReadModel extends PanacheEntityBase {

    @Id
    public String deviceCategoryId;
    public String deviceCategoryName;
    public String deviceCategoryDescription;

    public DeviceCategoryReadModel(){}

    public static DeviceCategoryReadModel createFromDeviceCategory(DeviceCategory deviceCategory){
        DeviceCategoryReadModel dcrm = new DeviceCategoryReadModel();
        dcrm.deviceCategoryId = deviceCategory.deviceCategoryId;
        dcrm.deviceCategoryName = deviceCategory.deviceCategoryName;
        dcrm.deviceCategoryDescription = deviceCategory.deviceCategoryDescription;
        return dcrm;
    }

}
