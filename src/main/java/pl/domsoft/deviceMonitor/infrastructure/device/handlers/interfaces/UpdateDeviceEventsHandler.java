package pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces;


import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.GenerateDeviceConfigsCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.UpdateDeviceEventsCommand;

/**
 * Created by szymo on 22.04.2017.
 * Interfejs handlera komendy {@link GenerateDeviceConfigsCommand}
 */
public interface UpdateDeviceEventsHandler extends CommandHandler<UpdateDeviceEventsCommand, Void> {
}
