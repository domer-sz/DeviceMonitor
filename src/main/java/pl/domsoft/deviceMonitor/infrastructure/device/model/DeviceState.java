package pl.domsoft.deviceMonitor.infrastructure.device.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.base.model.IdentifiableDevice;

import java.util.Date;
import java.util.Objects;

/**
 * Created by szymo on 23.04.2017.
 * Obiekt modelowy przechowujący informacje czy dane urzadzenie działa
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DeviceState implements IdentifiableDevice {
    protected final String deviceId;
    protected boolean on;
    protected final  boolean overview;
    protected final  boolean warning;
    protected final  int deviceTimeOn;
    protected final  Date logDate;

    @JsonCreator
    public DeviceState(
            @JsonProperty("deviceId") String deviceId,
            @JsonProperty("on") boolean on,
            @JsonProperty("overview") boolean overview,
            @JsonProperty("warning") boolean warning,
            @JsonProperty("deviceTimeOn") int deviceTimeOn,
            @JsonProperty("logDate") Date logDate) {
        this.deviceId = deviceId;
        this.on = on;
        this.overview = overview;
        this.warning = warning;
        this.deviceTimeOn = deviceTimeOn;
        this.logDate = logDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public boolean isOn() {
        return on;
    }
    @JsonIgnore
    public boolean isOff() {
        return !on;
    }

    public boolean overviewNeeded() {
        return overview;
    }

    public boolean warrningAlert() {
        return warning;
    }

    public int getDeviceTimeOn() {
        return deviceTimeOn;
    }

    public Date getLogDate() {
        return logDate;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(deviceId);
    }

    @Override
    public boolean equals(Object that)
    {
        return this == that || that instanceof DeviceState
                && Objects.equals(deviceId, ((DeviceState) that).deviceId);
    }

    @Override
    public String toString() {
        return "DeviceState{" +
                "deviceId='" + deviceId + '\'' +
                ", on=" + on +
                ", overview=" + overview +
                ", warning=" + warning +
                ", deviceTimeOn=" + deviceTimeOn +
                '}';
    }
    public void setOn(boolean on) {
        this.on = on;
    }
}
