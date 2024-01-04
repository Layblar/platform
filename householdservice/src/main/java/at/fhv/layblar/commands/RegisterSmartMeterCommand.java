package at.fhv.layblar.commands;

import jakarta.validation.constraints.NotNull;

public class RegisterSmartMeterCommand {

    @NotNull
    public String smartMeterId;
    
}
