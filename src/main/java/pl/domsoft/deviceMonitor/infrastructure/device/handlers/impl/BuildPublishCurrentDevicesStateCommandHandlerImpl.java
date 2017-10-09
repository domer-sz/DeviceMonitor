package pl.domsoft.deviceMonitor.infrastructure.device.handlers.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.BuildPublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.PublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.BuildPublishCurrentDevicesStateCommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceLogStateModel;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szymo on 23.04.2017.
 * Implementacja handlera komendy {@link PublishCurrentDevicesStateCommand}
 */
@Component
class BuildPublishCurrentDevicesStateCommandHandlerImpl implements BuildPublishCurrentDevicesStateCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(BuildPublishCurrentDevicesStateCommandHandlerImpl.class);

    @Value("${device.log.analyzer.rate.in.milliseconds}")
    private long logRateInMs;

    @Value("${number.of.cycles.to.alert}")
    private long numberOfCyclesToAlert;


    /**
     * Buduje komende która zawiera informacje o aktualnym stanie urządzń
     *
     *  command.lastLogs               - kolekcja zawierająca id urządzeń wraz z ostatnią datą logu urządzenia
     *  command.currentTimestamp       - obecny czas, na podstawie jego i czasu ostatniego logu urządzenia obliczany jest stan urządzenia
     *  command.allConfiguredDeviceIds
     * @return komenda z aktualnym stanem urządzeń
     */
    @Override
    public PublishCurrentDevicesStateCommand handle(BuildPublishCurrentDevicesStateCommand command) {
        PublishCurrentDevicesStateCommand sendStateCommand;
        List<String> devicesWithoutAnyLog = new ArrayList<>(command.getAllConfiguredDeviceIds());
        Set<DeviceState> deviceStates = new HashSet<>(command.getLastLogs().size());
        for (DeviceLogStateModel deviceLog : command.getLastLogs()) {
            DeviceState ds = deviceLog.checkDeviceState(command.getCurrentTimestamp(), logRateInMs, numberOfCyclesToAlert);
            deviceStates.add(ds);
            devicesWithoutAnyLog.remove(ds.getDeviceId());
        }
        if(!devicesWithoutAnyLog.isEmpty()){
            final List<DeviceState> neverOnDevices = devicesWithoutAnyLog.stream().map(id -> new DeviceState(id, false, false, false, 0, DateTimeUtils.localDateTimeToDate(command.getCurrentTimestamp()))).collect(Collectors.toList());
            deviceStates.addAll(neverOnDevices);
        }
        sendStateCommand = new PublishCurrentDevicesStateCommand(deviceStates);
        return sendStateCommand;
    }
}
