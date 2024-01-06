package at.fhv.layblar.infrastructure;

import java.util.LinkedList;
import java.util.List;

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

    public List<String> nativeQuery() {
        List<Object[]> list = entityManager.createNativeQuery("SELECT time_bucket('5 minutes', time) AS bucket, avg(v1_7_0) FROM smart_meter_data GROUP BY bucket ORDER BY bucket DESC").getResultList();

        List<String> result = new LinkedList<>();
        for(Object[] row : list) {
            result.add("{\"Bucket\": \"" + row[0] + "\",\"Average\": \"" + row[1] + "\"}");
        }
        return result;

    }
    
}
