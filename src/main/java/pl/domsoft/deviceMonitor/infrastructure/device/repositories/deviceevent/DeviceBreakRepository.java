package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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
public interface DeviceBreakRepository extends PagingAndSortingRepository<DeviceBreak, Long>{
    Page<DeviceBreak> findAllByDeviceId(String deviceId, Pageable pageable);
    Collection<DeviceBreak> findAll();

    @Query("SELECT b FROM DeviceBreak AS b WHERE b.startDate >= :startDate OR b.endDate <= :endDate")
    Stream<DeviceBreak> findAllInTimeRangeAndStream(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT b FROM DeviceBreak AS b WHERE b.notificationSent = false ORDER BY b.startDate DESC ")
    List<DeviceBreak> findEventsWithoutNotifications();
}
