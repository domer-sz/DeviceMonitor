package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by szymo on 23.04.2017.
 * interfejs rozszerzający repozytorium o dodatkowe metody z własną implementacją
 */
interface CustomDeviceEventRepository {
    @Transactional(readOnly = true)
    DeviceBreak findLastOpenDeviceBreak(String deviceId);
    @Transactional(readOnly = true)
    DeviceAccident findLastOpenDeviceAccident(String deviceId);
    @Transactional(readOnly = true)
    DeviceOverview findLastOpenDeviceOverview(String deviceId);
}
