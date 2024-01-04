package at.fhv.layblar.commands;

import jakarta.validation.constraints.NotNull;

public class RemoveSmartMeterCommand {

    @NotNull
    public String smartMeterId;
    
}
