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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deviceCategoryId == null) ? 0 : deviceCategoryId.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeviceCategory other = (DeviceCategory) obj;
        if (deviceCategoryId == null) {
            if (other.deviceCategoryId != null)
                return false;
        } else if (!deviceCategoryId.equals(other.deviceCategoryId))
            return false;
        return true;
    }

    
    
}
