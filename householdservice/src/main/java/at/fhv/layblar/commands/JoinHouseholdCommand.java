package at.fhv.layblar.commands;

import io.smallrye.common.constraint.NotNull;

public class JoinHouseholdCommand {

    @NotNull
    public String householdId;
    @NotNull
    public String userId;
    @NotNull
    public String joinCode;
    
}
