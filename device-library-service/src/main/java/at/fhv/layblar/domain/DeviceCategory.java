package at.fhv.layblar.domain;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity
public class DeviceCategory extends PanacheMongoEntityBase {

    @BsonId
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
