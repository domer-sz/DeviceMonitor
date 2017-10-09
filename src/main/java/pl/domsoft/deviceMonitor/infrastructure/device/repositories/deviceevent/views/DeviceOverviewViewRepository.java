package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.views.DeviceOverviewView;

/**
 * Created by szymo on 15.06.2017.
 */
@Repository
public interface DeviceOverviewViewRepository extends PagingAndSortingRepository<DeviceOverviewView, String>{
    Page<DeviceOverviewView> findAllByDeviceId(String deviceId, Pageable pageable);
}
