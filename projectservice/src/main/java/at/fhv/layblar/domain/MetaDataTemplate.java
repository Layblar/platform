package at.fhv.layblar.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MetaDataTemplate extends PanacheEntityBase {
    
    @Id
    public String metaDataId;
    public String metaDataName;
    public String type;

}
