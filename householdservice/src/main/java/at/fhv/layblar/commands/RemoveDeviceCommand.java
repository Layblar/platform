package at.fhv.layblar.commands;

import io.smallrye.common.constraint.NotNull;

public class RemoveDeviceCommand {

    @NotNull
    public String deviceId;

    public static RemoveDeviceCommand create(String deviceId) {
        RemoveDeviceCommand command = new RemoveDeviceCommand();
        command.deviceId = deviceId;
        return command;
    }
    
}
