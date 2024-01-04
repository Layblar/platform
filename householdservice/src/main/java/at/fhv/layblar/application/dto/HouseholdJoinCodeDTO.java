package at.fhv.layblar.application.dto;

import java.time.LocalDateTime;

import at.fhv.layblar.domain.HouseholdJoinCode;

public class HouseholdJoinCodeDTO {
    
    public String householdId;
    public String joinCode;
    public LocalDateTime validTill;

    public static HouseholdJoinCodeDTO createHouseholdJoinCodeDTO(HouseholdJoinCode joinCode) {
        HouseholdJoinCodeDTO dto = new HouseholdJoinCodeDTO();
        dto.householdId = joinCode.householdId;
        dto.joinCode = joinCode.joinCode;
        dto.validTill = joinCode.validTill;
        return dto;
    }

}
