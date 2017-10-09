package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig;

import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szymo on 23.04.2017.
 * implementacja rozszeżająca podstawowe repozytorium
 */
class DeviceConfigRepositoryImpl implements CustomDeviceConfigRepository {

    @Autowired
    private DeviceConfigRepository deviceConfigRepository;

    @Override
    public List<String> findAllReturnDeviceIds() {

        return deviceConfigRepository.findAll().stream().map(DeviceConfig::getDeviceId).collect(Collectors.toList());

    }
}
