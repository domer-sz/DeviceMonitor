package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;

import java.util.Collection;

/**
 * Created by szymo on 21.04.2017.
 * Repozytorium encji {@link DeviceConfig}
 */
@Repository
public interface DeviceConfigRepository extends CrudRepository<DeviceConfig, Long>, CustomDeviceConfigRepository{
    Collection<DeviceConfig> findAll();
    DeviceConfig findFirstDeviceConfigByDeviceId(String deviceId);
    Collection<DeviceConfig> findAllByDeviceIdIn(Iterable<String> deviceIds);
}
