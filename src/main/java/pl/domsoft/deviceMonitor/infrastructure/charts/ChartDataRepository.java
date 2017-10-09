package pl.domsoft.deviceMonitor.infrastructure.charts;

import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.charts.model.ChartDataModel;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog.DeviceLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities.CustomLogFieldConfig;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.repositories.CustomLogFieldConfigRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szymo on 07.06.2017.
 */
@Component
public class ChartDataRepository {

    private final DeviceLogRepository deviceLogRepository;
    private final CustomLogFieldConfigRepository customLogFieldConfigRepository;

    public ChartDataRepository(DeviceLogRepository deviceLogRepository, CustomLogFieldConfigRepository customLogFieldConfigRepository) {
        this.deviceLogRepository = deviceLogRepository;
        this.customLogFieldConfigRepository = customLogFieldConfigRepository;
    }

    public ChartDataModel getDataForChart(LoadChartDataModel loadChartDataModel){
        return deviceLogRepository.findLastLogs(
                loadChartDataModel.getDeviceId(),
                loadChartDataModel.getStartDate(),
                loadChartDataModel.getLastDate(),
                getLogFieldConfigAsList(loadChartDataModel)
        );
    }

    private List<CustomLogFieldConfig> getLogFieldConfigAsList(LoadChartDataModel loadChartDataModel) {
        final Iterable<CustomLogFieldConfig> logFields = customLogFieldConfigRepository.findAll(loadChartDataModel.getLogFieldIds());
        List<CustomLogFieldConfig> list = new ArrayList<>();
        for (CustomLogFieldConfig field : logFields) {
            list.add(field);
        }
        return list;
    }


}
