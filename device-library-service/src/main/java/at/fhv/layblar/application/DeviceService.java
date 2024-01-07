package at.fhv.layblar.application;

import java.util.List;

import at.fhv.layblar.utils.exceptions.DeviceCategoryNotFoundException;
import at.fhv.layblar.utils.exceptions.DeviceNotFoundException;

public interface DeviceService {

    public DeviceDTO getDeviceById(String deviceId) throws DeviceNotFoundException;

    public DeviceCategoryDTO getCategoryById(String categoryId) throws DeviceCategoryNotFoundException;

    public List<DeviceDTO> listDevices(String name);

    public List<DeviceCategoryDTO> listCategories(String name);
}
