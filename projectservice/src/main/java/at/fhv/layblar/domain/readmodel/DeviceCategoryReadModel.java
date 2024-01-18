package at.fhv.layblar.domain.readmodel;

import java.util.List;

import at.fhv.layblar.domain.model.DeviceCategory;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class DeviceCategoryReadModel extends PanacheEntityBase {

    @Id
    public String deviceCategoryId;
    public String deviceCategoryName;
    public String deviceCategoryDescription;
    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    public List<LabelReadModel> labels;

    public DeviceCategoryReadModel(){}

    public static DeviceCategoryReadModel createFromDeviceCategory(DeviceCategory deviceCategory){
        DeviceCategoryReadModel dcrm = new DeviceCategoryReadModel();
        dcrm.deviceCategoryId = deviceCategory.deviceCategoryId;
        dcrm.deviceCategoryName = deviceCategory.deviceCategoryName;
        dcrm.deviceCategoryDescription = deviceCategory.deviceCategoryDescription;
        return dcrm;
    }

}
