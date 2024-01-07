package at.fhv.layblar.application;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.Device;
import at.fhv.layblar.domain.DeviceCategory;
import at.fhv.layblar.utils.exceptions.DeviceCategoryNotFoundException;
import at.fhv.layblar.utils.exceptions.DeviceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeviceServiceImpl implements DeviceService {

    @Override
    public DeviceDTO getDeviceById(String deviceId) throws DeviceNotFoundException {
        Optional<Device> optDevice = Device.findByIdOptional(deviceId);
        if (optDevice.isPresent()) {
            return DeviceDTO.createDTO(optDevice.get());
        }
        throw new DeviceNotFoundException();
    }

    @Override
    public DeviceCategoryDTO getCategoryById(String categoryId) throws DeviceCategoryNotFoundException {
        List<DeviceCategory> categories = Device.find("deviceCategory.deviceCategoryId", categoryId)
        .project(DeviceCategory.class).list()
        .stream()
        .distinct()
        .collect(Collectors.toList());
        if(!categories.isEmpty()){
            return DeviceCategoryDTO.createDeviceCategoryDTO(categories.get(0));
        }
        throw new DeviceCategoryNotFoundException();
    }

    @Override
    public List<DeviceDTO> listDevices(String name) {
        List<Device> devices = new LinkedList<>();
        if(name != null){
            devices = Device.find("deviceName", name).list();
        } else {
            devices = Device.listAll();
        }
        return devices.stream().map(device -> DeviceDTO.createDTO(device)).collect(Collectors.toList());
    }

    @Override
    public List<DeviceCategoryDTO> listCategories(String name) {
        List<DeviceCategory> categories = Device.findAll().project(DeviceCategory.class).list().stream().distinct()
                .collect(Collectors.toList());
        return categories.stream().map(category -> DeviceCategoryDTO.createDeviceCategoryDTO(category))
                .collect(Collectors.toList());
    }

}
