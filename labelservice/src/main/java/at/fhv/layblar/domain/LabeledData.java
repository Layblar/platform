package at.fhv.layblar.domain;

import java.time.LocalDateTime;

import at.fhv.layblar.application.dto.LabelMetaDataDTO;
import at.fhv.layblar.application.dto.SmartMeterDataDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Id;

public class LabeledData extends PanacheEntityBase {

    @Id
    public String labelId;
    public String householdId;
    public SmartMeterData smartMeterData;
    public String label;
    public LocalDateTime createdAt;
    public static LabeledData create(Object claim, SmartMeterDataDTO smartMeterDataDTO, String label2,
            LabelMetaDataDTO labelMetaData) {
        return null;
    }

    

}
