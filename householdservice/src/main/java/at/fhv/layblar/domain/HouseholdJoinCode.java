package at.fhv.layblar.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class HouseholdJoinCode extends PanacheEntity {
    
    public String householdId;
    public String joinCode;
    public LocalDateTime createdAt;
    public LocalDateTime validTill;

    public HouseholdJoinCode(){};

    private HouseholdJoinCode(String householdId) {
        this.householdId = householdId;
        this.joinCode = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.validTill = createdAt.plus(7, ChronoUnit.DAYS);
    }


    public static HouseholdJoinCode create(String householdId) {
        return new HouseholdJoinCode(householdId);
    }

}
