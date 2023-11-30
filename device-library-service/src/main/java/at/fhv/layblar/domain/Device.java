package at.fhv.layblar.domain;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity
public class Device extends PanacheMongoEntityBase {
    
    @BsonId
    public String deviceId;
    public String displayName;
    public String manufacturer;
    public List<String> names;
}
