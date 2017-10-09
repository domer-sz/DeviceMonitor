package pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceLog;

import java.util.Collection;
import java.util.Date;

/**
 * Created by szymo on 21.04.2017.
 * Repozytorium encji {@link DeviceLog}
 */
@Repository
public interface DeviceLogRepository extends CrudRepository<DeviceLog, Long>, CustomDeviceLogRepo {

    Collection<DeviceLog> findAll();
    @Transactional
    @Modifying
    @Query("DELETE FROM DeviceLog l WHERE l.logTimestamp < :limitDate")
    void deleteOldLogs(@Param("limitDate") Date limitDate);
}
