package pl.domsoft.deviceMonitor.infrastructure.device.commands;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.BuildPublishCurrentDevicesStateCommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceLogStateModel;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by szymo on 03.05.2017.
 * Komenda budująca komędę PublishCurrentDevicesStateCommand
 */
public class BuildPublishCurrentDevicesStateCommand implements Command{

    final private Collection<DeviceLogStateModel> lastLogs;
    final private LocalDateTime currentTimestamp;
    final private List<String> allConfiguredDeviceIds;

    public BuildPublishCurrentDevicesStateCommand(Collection<DeviceLogStateModel> lastLogs, LocalDateTime currentTimestamp, List<String> allConfiguredDeviceIds) {
        this.lastLogs = lastLogs;
        this.currentTimestamp = currentTimestamp;
        this.allConfiguredDeviceIds = allConfiguredDeviceIds;
    }

    public Collection<DeviceLogStateModel> getLastLogs() {
        return lastLogs;
    }

    public LocalDateTime getCurrentTimestamp() {
        return currentTimestamp;
    }

    public List<String> getAllConfiguredDeviceIds() {
        return allConfiguredDeviceIds;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return BuildPublishCurrentDevicesStateCommandHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return false;
    }
}
