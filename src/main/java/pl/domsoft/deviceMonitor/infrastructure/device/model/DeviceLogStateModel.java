package pl.domsoft.deviceMonitor.infrastructure.device.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.domsoft.deviceMonitor.application.deviceloganalyzer.DeviceLogAnalyzer;
import pl.domsoft.deviceMonitor.infrastructure.base.model.IdentifiableDevice;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Created by szymo on 23.04.2017.
 * Simple model used to device state analyze
 */
public class DeviceLogStateModel implements IdentifiableDevice {

    private static final Logger log = LoggerFactory.getLogger(DeviceLogAnalyzer.class);

    /**
     * id urządzenia które wysłało log
     */
    private final String deviceId;
    /**
     * data ostatniego logu
     */
    private final Date lastLogDate;
    /**
     * wartość z portu przekazującego info o awarii
     */
    private final int warningPort;
    /**
     * wartość portu informującego o przeglądzie
     */
    private final int overviewPort;
    /**
     * wartość portu informująca o czasie działania urządzenia
     */
    private final int deviceTimeOnPortValue;

    public DeviceLogStateModel(String deviceId, Date lastLogDate, int warningPort, int overviewPort, int deviceTimeOnPortValue) {
        this.deviceId = deviceId;
        this.lastLogDate = lastLogDate;
        this.warningPort = warningPort;
        this.overviewPort = overviewPort;
        this.deviceTimeOnPortValue = deviceTimeOnPortValue;
    }

    public String getDeviceId() {
        return deviceId;
    }
    public int getDeviceTimeOnPortValue(){return deviceTimeOnPortValue;}

    /**
     * Zwraca obecny stan urządzenia na podstawie przykazanych parametów i swojego stanu
     * @param currentDate - obecna data, od której będzie liczony czas od ostatniego logu
     * @param logRateInMs - dłgość cyklu analizatora w milisekundach
     * @param cyclesToAlarm - ilość cykli które muszą minąć aby oznaczyć urządzenie jako nieaktywne
     * @return
     */
    public DeviceState checkDeviceState(LocalDateTime currentDate, long logRateInMs, long cyclesToAlarm){
        final LocalDateTime localDateTime = currentDate.minusSeconds(((logRateInMs * cyclesToAlarm) / 1000));
        boolean deviceOn = localDateTime.isBefore(DateTimeUtils.dateToLocalDateTime(this.lastLogDate));
        return new DeviceState(this.deviceId, deviceOn, overviewPort > 5, warningPort > 5, deviceTimeOnPortValue, lastLogDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(deviceId);
    }

    @Override
    public boolean equals(Object that)
    {
        return this == that || that instanceof DeviceLogStateModel
                && Objects.equals(deviceId, ((DeviceLogStateModel) that).deviceId);
    }
}
