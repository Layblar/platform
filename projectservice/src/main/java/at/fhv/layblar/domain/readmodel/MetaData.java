package at.fhv.layblar.domain.readmodel;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import at.fhv.layblar.domain.model.ProjectMetaData;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MetaData extends PanacheEntityBase {
    
    @Id
    public String metaDataId;
    @JdbcTypeCode(SqlTypes.JSON)
    public List<ProjectMetaData> householdMetaData;

}
