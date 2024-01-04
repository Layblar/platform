package at.fhv.layblar.commands;

import io.smallrye.common.constraint.NotNull;

public class LeaveHouseholdCommand {

    @NotNull
    public String householdId;
    @NotNull
    public String userId;


    public static LeaveHouseholdCommand create(String householdId, String userId){
        LeaveHouseholdCommand command = new LeaveHouseholdCommand();
        command.householdId = householdId;
        command.userId = userId;
        return command;
    }
    
}
