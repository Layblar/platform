package at.fhv.layblar.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DeviceCategory extends PanacheEntityBase {

    @Id
    public String deviceCategoryId;
    public String deviceCategoryName;
    public String deviceCategoryDescription;


    public DeviceCategory(){}
    
}
