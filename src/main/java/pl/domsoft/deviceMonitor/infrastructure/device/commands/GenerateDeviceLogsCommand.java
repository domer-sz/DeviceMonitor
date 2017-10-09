package pl.domsoft.deviceMonitor.infrastructure.device.commands;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.GenerateDeviceLogsHandler;

import java.util.Date;
import java.util.List;

/**
 * Created by szymo on 23.04.2017.
 * Komenda generująca logi urzadzen
 */
public class GenerateDeviceLogsCommand implements Command {

    /**
     * poczatek generowania logów
     */
    private final Date start;
    /**
     * koniec generowania logów
     */
    private final Date end;
    /**
     * odstępy w sekundach pomiędzi następnmi logami
     */
    private final int densityInSec;
    /**
     * lista id urządzeń dla których logi będą generowane
     */
    private final List<String> deviceIds;

    public GenerateDeviceLogsCommand(Date start, Date end, int densityInSec, List<String> deviceIds) {
        this.start = start;
        this.end = end;
        this.densityInSec = densityInSec;
        this.deviceIds = deviceIds;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getDensityInSec() {
        return densityInSec;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return GenerateDeviceLogsHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
