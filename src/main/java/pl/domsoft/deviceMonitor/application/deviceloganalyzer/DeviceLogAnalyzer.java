package pl.domsoft.deviceMonitor.application.deviceloganalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.BuildPublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.PublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceLogStateModel;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog.DeviceLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by szymo on 23.04.2017.
 * Analizuje logi urządzń i na ich podstawie wyznacza stan danego urządzenia (działa / nie działa)
 */
@Component
public class DeviceLogAnalyzer {
    private static final Logger log = LoggerFactory.getLogger(DeviceLogAnalyzer.class);

    private final CommandBus commandBus;
    private final DeviceLogRepository queryDeviceLogRepo;
    private final DeviceConfigRepository deviceConfigRepository;

    @Value("${device.log.analyzer.on}")
    private boolean deviceLogAnalyzerOn;

    public DeviceLogAnalyzer(CommandBus commandBus, DeviceLogRepository queryDeviceLogRepo, DeviceConfigRepository deviceConfigRepository) {
        this.commandBus = commandBus;
        this.queryDeviceLogRepo = queryDeviceLogRepo;
        this.deviceConfigRepository = deviceConfigRepository;
    }

    @Scheduled(
            fixedRateString = "${device.log.analyzer.rate.in.milliseconds}",
            initialDelayString ="${device.log.analyzer.rate.in.milliseconds}")
    public void scheduleLogAnalyze() {
        if (deviceLogAnalyzerOn) {
            LocalDateTime currentTimestamp = DateTimeUtils.getCurrentLocalDateTime();
            Collection<DeviceLogStateModel> lastLogs = queryDeviceLogRepo.findLastLogs();
            final List<String> allConfiguredDeviceIds = deviceConfigRepository.findAllReturnDeviceIds();
            PublishCurrentDevicesStateCommand publishStateCommand = null;
            try {
                publishStateCommand = commandBus.sendCommand(
                        new BuildPublishCurrentDevicesStateCommand(
                            lastLogs,
                            currentTimestamp,
                            allConfiguredDeviceIds
                        ),
                        PublishCurrentDevicesStateCommand.class
                );

                commandBus.sendCommand(publishStateCommand);
            } catch (AppException e) {
                e.printStackTrace();
                //TODO: wyślij maila z błędem na adres zdefiniowany w configu
            }

        }
    }


}
