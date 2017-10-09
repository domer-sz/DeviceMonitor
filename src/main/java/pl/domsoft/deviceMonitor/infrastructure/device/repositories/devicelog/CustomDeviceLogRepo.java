package pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog;

import org.springframework.transaction.annotation.Transactional;
import pl.domsoft.deviceMonitor.infrastructure.charts.model.ChartDataModel;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceFullData;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceLogStateModel;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;
import pl.domsoft.deviceMonitor.infrastructure.shared.model.pairs.Pair;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities.CustomLogFieldConfig;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by szymo on 23.04.2017.
 */
interface CustomDeviceLogRepo {

    @Transactional(readOnly = true)
    Collection<DeviceLogStateModel> findLastLogs();

    @Transactional(readOnly = true)
    Collection<DeviceLogStateModel> findLastLogs(Collection<String> deiceIds);

    @Transactional(readOnly = true)
    List<DeviceState> findLastLogs(String deviceId, int statesNumber);

    @Transactional(readOnly = true)
    ChartDataModel findLastLogs(String deviceId, Date startDate, Date endDate, List<CustomLogFieldConfig> fields);

    DeviceFullData findLastDeviceData(String deviceId);
}
