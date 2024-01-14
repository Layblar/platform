package at.fhv.layblar.domain.readmodel;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class ProjectLabeledData extends PanacheEntity {

    public String labeledDataId;
    public String projectId;
    public LocalDateTime validFrom;
    public LocalDateTime validTo;
    public String metaDataId;
    
}
