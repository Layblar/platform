package at.fhv.layblar.authentication.dto;

import java.util.List;

import at.fhv.layblar.authentication.model.LayblarAccount;

public class AccountDTO {

    public String userId;
    public String researcherId;
    public String email;
    public String householdId;
    public List<String> roles;

    public AccountDTO(){};

    public AccountDTO(String userId, String researcherId, String email, String householdId, List<String> roles) {
        this.userId = userId;
        this.researcherId = researcherId;
        this.email = email;
        this.householdId = householdId;
        this.roles = roles;
    }

    public static AccountDTO createAccountDTO(LayblarAccount user) {
        return new AccountDTO(user.userId, user.researcherId, user.email, user.householdId, user.roles);
    }

    
    
}
