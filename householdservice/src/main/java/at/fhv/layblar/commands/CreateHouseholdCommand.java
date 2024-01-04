package at.fhv.layblar.commands;

import jakarta.validation.constraints.NotNull;

public class CreateHouseholdCommand {

    @NotNull
    public String email;
    @NotNull
    public String firstName;
    @NotNull
    public String lastName;

}
