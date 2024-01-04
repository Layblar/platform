package at.fhv.layblar.infrastructure;

import java.util.Optional;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import at.fhv.layblar.domain.Device;
import at.fhv.layblar.events.DeviceAddedEvent;
import at.fhv.layblar.events.DeviceDeletedEvent;
import at.fhv.layblar.events.DeviceEvent;
import at.fhv.layblar.events.DeviceEventVisitor;
import at.fhv.layblar.events.DeviceUpdatedEvent;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HouseholdDeviceConsumer {

    @Incoming("device")
    public void consume(DeviceEvent deviceEvent){

        Optional<Device> optDevice = Device.findByIdOptional(deviceEvent.entityId);
        Device device = new Device();
        if(optDevice.isPresent()){
            device = optDevice.get();
        }
        device = handleDeviceEvent(device, deviceEvent);
        Device.persistOrUpdate(device);
    }

    private Device handleDeviceEvent(Device device, DeviceEvent deviceEvent){
        deviceEvent.accept(new DeviceEventVisitor() {

            @Override
            public void visit(DeviceAddedEvent event) {
                if(device.deviceId == null){
                    device.createDevice(event);
                } else {
                    device.apply(event);
                }
            }

            @Override
            public void visit(DeviceUpdatedEvent event) {
                device.apply(event);
            }

            @Override
            public void visit(DeviceDeletedEvent event) {
                device.apply(event);
            }
            
        });
        return device;
    }

    
}
