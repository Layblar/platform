package at.fhv.layblar.domain;

import java.util.LinkedList;
import java.util.List;
import at.fhv.layblar.commands.AddDeviceCommand;
import at.fhv.layblar.commands.CreateHouseholdCommand;
import at.fhv.layblar.commands.JoinHouseholdCommand;
import at.fhv.layblar.commands.LeaveHouseholdCommand;
import at.fhv.layblar.commands.RegisterSmartMeterCommand;
import at.fhv.layblar.commands.RemoveDeviceCommand;
import at.fhv.layblar.commands.RemoveSmartMeterCommand;
import at.fhv.layblar.commands.UpdateDeviceCommand;
import at.fhv.layblar.events.DeviceAddedEvent;
import at.fhv.layblar.events.DeviceRemovedEvent;
import at.fhv.layblar.events.DeviceUpdatedEvent;
import at.fhv.layblar.events.HouseholdCreatedEvent;
import at.fhv.layblar.events.HouseholdDeletedEvent;
import at.fhv.layblar.events.HouseholdUserJoinedEvent;
import at.fhv.layblar.events.HouseholdUserLeftEvent;
import at.fhv.layblar.events.SmartMeterRegisteredEvent;
import at.fhv.layblar.events.SmartMeterRemovedEvent;
import at.fhv.layblar.utils.exceptions.DeviceNotFoundException;
import at.fhv.layblar.utils.exceptions.SmartMeterAlreadyRegisteredException;
import at.fhv.layblar.utils.exceptions.SmartMeterNotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Household extends PanacheEntityBase {
    @Id
    public String householdId;
    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<HouseholdUser> users;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Device> devices;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<SmartMeter> smartMeters;

    public Household(){}

    public boolean has() {
        return users.isEmpty();
    }

    public void apply(HouseholdCreatedEvent event) {
        this.householdId = event.entityId;
        this.users = new LinkedList<>();
        this.users.add(HouseholdUser.createUser(event.getUserId(), event.getEmail(), event.getFirstName(), event.getLastName()));
        this.devices = new LinkedList<>();
        this.smartMeters = new LinkedList<>();
    }

    public void apply(HouseholdUserJoinedEvent event) {
        this.users.add(HouseholdUser.createUser(event.getUserId(), event.getEmail(), event.getFirstName(), event.getLastName()));
    }

    public void apply(HouseholdUserLeftEvent event) {
        this.users.removeIf(user -> user.userId.equals(event.getUserId()));
    }

    public void apply(SmartMeterRegisteredEvent event) {
        this.smartMeters.add(SmartMeter.create(event.getSmartMeterId()));
    }

    public void apply(SmartMeterRemovedEvent event) {
        this.smartMeters.removeIf(smartMeter -> smartMeter.smartMeterId.equals(event.getSmartMeterId()));
    }

    public void apply(DeviceAddedEvent event) {
        Device device = Device.create(event);
        this.devices.add(device);
    }

    public void apply(DeviceUpdatedEvent event) {
        Device device = this.devices.stream().filter(d -> d.deviceId.equals(event.getDeviceId())).findFirst().get();
        this.devices.remove(device);
        device.updateDeviceInformation(event);
        this.devices.add(device);
    }

    public void apply(DeviceRemovedEvent event) {
        Device device = this.devices.stream().filter(d -> d.deviceId.equals(event.getDeviceId())).findFirst().get();
        this.devices.remove(device);
    }

    public void apply(HouseholdDeletedEvent event) {
    }

    public HouseholdCreatedEvent process(CreateHouseholdCommand createHouseholdCommand){
        return HouseholdCreatedEvent.create(createHouseholdCommand);
    }

    public HouseholdUserLeftEvent process(LeaveHouseholdCommand leaveHouseholdCommand) {
        return null;
    }

    public HouseholdUserJoinedEvent process(JoinHouseholdCommand command) {
        return null;
    }

    public DeviceAddedEvent process(AddDeviceCommand command) {
        return DeviceAddedEvent.create(command, this);
    }

    public DeviceUpdatedEvent process(UpdateDeviceCommand command) throws DeviceNotFoundException{
        if(this.devices.stream().anyMatch(device -> device.deviceId.equals(command.deviceId))){
            return DeviceUpdatedEvent.create(command, this);
        }
        throw new DeviceNotFoundException();
    }

    public DeviceRemovedEvent process(RemoveDeviceCommand command) throws DeviceNotFoundException {
        if(this.devices.stream().anyMatch(device -> device.deviceId.equals(command.deviceId))){
            return DeviceRemovedEvent.create(command, this);
        }
        throw new DeviceNotFoundException();
    }

    public SmartMeterRegisteredEvent process(RegisterSmartMeterCommand command) throws SmartMeterAlreadyRegisteredException {
        if(this.smartMeters.stream().anyMatch(smartMeter -> smartMeter.smartMeterId.equals(command.smartMeterId))){
            throw new SmartMeterAlreadyRegisteredException();
        }
        return SmartMeterRegisteredEvent.create(command, this);
    }

    public SmartMeterRemovedEvent process(RemoveSmartMeterCommand command) throws SmartMeterNotFoundException {
        if(this.smartMeters.stream().anyMatch(smartMeter -> smartMeter.smartMeterId.equals(command.smartMeterId))){
            return SmartMeterRemovedEvent.create(command, this);
        }
        throw new SmartMeterNotFoundException();
    }

}
