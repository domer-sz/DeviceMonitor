package pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.GenerateDeviceLogsCommand;

/**
 * Created by szymo on 23.04.2017.
 * Interfejs handlera komendy {@link GenerateDeviceLogsCommand}
 */
public interface GenerateDeviceLogsHandler extends CommandHandler<GenerateDeviceLogsCommand, String> {
}
