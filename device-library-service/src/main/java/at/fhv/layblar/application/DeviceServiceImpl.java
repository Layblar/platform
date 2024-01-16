package at.fhv.layblar.application;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.DeviceCategory;
import at.fhv.layblar.domain.Device;
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

        List<Device> devices = Device.listAll();

        for (Device device : devices) {
            for (DeviceCategory category : device.deviceCategory) {
                if (category.deviceCategoryId.equals(categoryId)) {
                    return DeviceCategoryDTO.createDeviceCategoryDTO(category);
                }
            }
        }

        throw new DeviceCategoryNotFoundException();

    }

    @Override
    public List<DeviceDTO> listDevices(String name) {
        List<Device> devices = new LinkedList<>();
        if(name != null){
            devices = Device.find("{'$or': [{'deviceName': ?1}, {'alternativeNames': {'$in': [?1]}}]}", name).list();
        } else {
            devices = Device.listAll();
        }
        return devices.stream().map(device -> DeviceDTO.createDTO(device)).collect(Collectors.toList());
    }

    @Override
    public List<DeviceCategoryDTO> listCategories(String name) {
        List<Device> devices = Device.listAll();

        List<DeviceCategory> categories = new LinkedList<>();

        for (Device device : devices) {
            for (DeviceCategory category : device.deviceCategory) {
                if (name != null) {
                    if (category.deviceCategoryName.equals(name)) {
                        categories.add(category);
                    }
                } else {
                    categories.add(category);
                }
            }
        }

        categories = categories.stream().distinct().collect(Collectors.toList());

        return categories.stream().map(category -> DeviceCategoryDTO.createDeviceCategoryDTO(category)).collect(Collectors.toList());

    }
}
