package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.views.DeviceBreakView;

/**
 * Created by szymo on 15.06.2017.
 */
@Repository
public interface DeviceBreakViewRepository extends PagingAndSortingRepository<DeviceBreakView, String>{
    Page<DeviceBreakView> findAllByDeviceId(String deviceId, Pageable pageable);
}
