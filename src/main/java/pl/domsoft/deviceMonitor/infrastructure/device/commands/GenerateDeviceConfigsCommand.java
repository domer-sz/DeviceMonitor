package pl.domsoft.deviceMonitor.infrastructure.device.commands;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.GenerateDeviceConfigsHandler;

/**
 * Created by szymo on 22.04.2017.
 * Komenda generująca n konfiguracji
 */
public class GenerateDeviceConfigsCommand implements Command {
    private final int numberOfConfigsToGenerate;

    public GenerateDeviceConfigsCommand(int numberOfConfigsToGenerate) {
        this.numberOfConfigsToGenerate = numberOfConfigsToGenerate;
    }

    public int getNumberOfConfigsToGenerate() {
        return numberOfConfigsToGenerate;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return GenerateDeviceConfigsHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
