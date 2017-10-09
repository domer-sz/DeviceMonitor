package pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.PublishCurrentDevicesStateCommand;

/**
 * Created by szymo on 29.04.2017.
 * Interfejs handlera komendy {@link pl.domsoft.deviceMonitor.infrastructure.device.commands.PublishCurrentDevicesStateCommand}
 */
public interface PublishCurrentDevicesStateHandler extends CommandHandler<PublishCurrentDevicesStateCommand, Void> {
}
