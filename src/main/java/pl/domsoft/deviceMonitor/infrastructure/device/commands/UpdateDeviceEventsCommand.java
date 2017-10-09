package pl.domsoft.deviceMonitor.infrastructure.device.commands;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.UpdateDeviceEventsHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;

/**
 * Created by szymo on 22.04.2017.
 * Komenda aktualizująca zdarzenia dla danego urządzenia
 */
public class UpdateDeviceEventsCommand implements Command {

    private final DeviceState oldSavedState;
    private final DeviceState newState;

    public UpdateDeviceEventsCommand(DeviceState oldSavedState, DeviceState newState) {
        this.oldSavedState = oldSavedState;
        this.newState = newState;
    }

    public DeviceState getOldSavedState() {
        return oldSavedState;
    }

    public DeviceState getNewState() {
        return newState;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return UpdateDeviceEventsHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return false;
    }
}
