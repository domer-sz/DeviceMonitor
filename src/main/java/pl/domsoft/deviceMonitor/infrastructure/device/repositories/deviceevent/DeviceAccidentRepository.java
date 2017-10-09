package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceEvent;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by szymo on 03.05.2017.
 * Repozytorium encji {@link DeviceEvent}
 */
@Repository
public interface DeviceAccidentRepository extends PagingAndSortingRepository<DeviceAccident, Long>{
    Page<DeviceAccident> findAllByDeviceId(String deviceId, Pageable pageable);
    Collection<DeviceAccident> findAll();

    @Query("SELECT a FROM DeviceAccident AS a WHERE a.startDate >= :startDate OR (a.endDate <= :endDate AND a.startDate >= :startDate)")
    Stream<DeviceAccident> findAllInTimeRangeAndStream(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT a FROM DeviceAccident AS a WHERE a.notificationSent = false ORDER BY a.startDate DESC ")
    List<DeviceAccident> findEventsWithoutNotifications();
}
