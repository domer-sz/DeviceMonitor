package pl.domsoft.deviceMonitor.infrastructure.device.commands;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.PublishCurrentDevicesStateHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;

import java.util.Set;

/**
 * Created by szymo on 23.04.2017.
 * Komenda wysłania obecnego stanu urządzeń do przeglądarki
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PublishCurrentDevicesStateCommand implements Command {
    private final Set<DeviceState> deviceStates;

    private PublishCurrentDevicesStateCommand(){
        deviceStates = null;
    }

    @JsonCreator
    public PublishCurrentDevicesStateCommand( @JsonProperty("deviceStates") Set<DeviceState> deviceStates ) {
        this.deviceStates = deviceStates;
    }

    public Set<DeviceState> getDeviceStates() {
        return deviceStates;
    }

    @Override
    @JsonIgnore
    public Class<? extends CommandHandler> getHandlerClass() {
        return PublishCurrentDevicesStateHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return false;
    }

    @Override
    public String toString() {
        return "PublishCurrentDevicesStateCommand{" +
                "deviceStates=" + deviceStates +
                '}';
    }
}
