package pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.BuildPublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.PublishCurrentDevicesStateCommand;

/**
 * Created by szymo on 03.05.2017.
 */
public interface BuildPublishCurrentDevicesStateCommandHandler extends CommandHandler<BuildPublishCurrentDevicesStateCommand,PublishCurrentDevicesStateCommand> {
}
