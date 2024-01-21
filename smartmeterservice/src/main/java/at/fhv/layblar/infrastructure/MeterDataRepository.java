package at.fhv.layblar.infrastructure;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.MeterDataReading;
import at.fhv.layblar.domain.MeterDataReadingKey;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class MeterDataRepository implements PanacheRepositoryBase<MeterDataReading,MeterDataReadingKey> {

    @Inject
    EntityManager entityManager;

    public List<MeterDataReading> findByHouseholdId(String interval, LocalDateTime from, LocalDateTime to, String householdId) {
        List<Object[]> list = entityManager.createNativeQuery(
            "SELECT time_bucket( cast(:interval as interval) , time) AS bucket, householdId, sensorId, " +
            "AVG(v1_7_0) AS v1_7_0, " +
            "AVG(v1_8_0) AS v1_8_0, " +
            "AVG(v2_7_0) AS v2_7_0, " +
            "AVG(v2_8_0) AS v2_8_0, " +
            "AVG(v3_8_0) AS v3_8_0, " +
            "AVG(v4_8_0) AS v4_8_0, " +
            "AVG(v16_7_0) AS v16_7_0, " +
            "AVG(v31_7_0) AS v31_7_0, " +
            "AVG(v32_7_0) AS v32_7_0, " +
            "AVG(v51_7_0) AS v51_7_0, " +
            "AVG(v52_7_0) AS v52_7_0, " +
            "AVG(v71_7_0) AS v71_7_0, " +
            "AVG(v72_7_0) AS v72_7_0 " + 
            "FROM smart_meter_data WHERE householdId = :householdId AND time BETWEEN :from AND :to " +
            "GROUP BY bucket, householdId, sensorId ORDER BY bucket ASC")
            .setParameter("interval", interval)
            .setParameter("householdId", householdId)
            .setParameter("from", from)
            .setParameter("to", to)
            .getResultList();


        return list.stream().map(object -> {
            MeterDataReading mdr = new MeterDataReading();
            mdr.time = LocalDateTime.ofInstant((Instant) object[0], ZoneId.systemDefault());
            mdr.householdId = (String) object[1];
            mdr.sensorId = (String) object[2];
            mdr.v1_7_0 = ((Double) object[3]).floatValue();
            mdr.v1_8_0 = ((Double) object[4]).floatValue();
            mdr.v2_7_0 = ((Double) object[5]).floatValue();
            mdr.v2_8_0 = ((Double) object[6]).floatValue();
            mdr.v3_8_0 = ((Double) object[7]).floatValue();
            mdr.v4_8_0 = ((Double) object[8]).floatValue();
            mdr.v16_7_0 = ((Double) object[9]).floatValue();
            mdr.v31_7_0 = ((Double) object[10]).floatValue();
            mdr.v32_7_0 = ((Double) object[11]).floatValue();
            mdr.v51_7_0 = ((Double) object[12]).floatValue();
            mdr.v52_7_0 = ((Double) object[13]).floatValue();
            mdr.v71_7_0 = ((Double) object[14]).floatValue();
            mdr.v72_7_0 = ((Double) object[15]).floatValue();
            return mdr; 
        }).collect(Collectors.toList());
    }
    
}
