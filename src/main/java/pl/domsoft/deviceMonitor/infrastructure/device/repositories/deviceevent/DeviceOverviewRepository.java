package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceEvent;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by szymo on 03.05.2017.
 * Repozytorium encji {@link DeviceEvent}
 */
@Repository
public interface DeviceOverviewRepository extends PagingAndSortingRepository<DeviceOverview, Long>{
    Page<DeviceOverview> findAllByDeviceId(String deviceId, Pageable pageable);
    Collection<DeviceOverview> findAll();

    @Query("SELECT o FROM DeviceOverview AS o WHERE o.startDate >= :startDate OR (o.endDate <= :endDate AND o.startDate >= :startDate)")
    Stream<DeviceOverview> findAllInTimeRangeAndStream(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT o FROM DeviceOverview AS o WHERE o.notificationSent = false ORDER BY o.startDate DESC ")
    List<DeviceOverview> findEventsWithoutNotifications();
}
