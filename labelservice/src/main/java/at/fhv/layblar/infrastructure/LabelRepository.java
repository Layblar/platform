package at.fhv.layblar.infrastructure;

import java.util.List;

import at.fhv.layblar.domain.LabeledData;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public class LabelRepository implements PanacheRepositoryBase<LabeledData,String>{

    public List<LabeledData> findByHouseholdId(String householdId) {
        return list("householdId", householdId);
    }
    
}
