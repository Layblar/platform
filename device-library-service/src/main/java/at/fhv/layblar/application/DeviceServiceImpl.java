package at.fhv.layblar.application;

import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.Device;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeviceServiceImpl implements DeviceService {

    @Override
    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = Device.listAll();
        return devices.stream().map(device -> DeviceDTO.createDTO(device)).collect(Collectors.toList());
    }
    
}
