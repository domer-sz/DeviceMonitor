package pl.domsoft.deviceMonitor.infrastructure.actions.commands.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.action.CreateActionHandler;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;

import java.util.List;

/**
 * Created by szymo on 18.06.2017.
 */
public class CreateActionCommand implements Command{

    private final Long actionTypeId;
    private final List<String> deviceId;
    private final String additionalParams;
    @JsonIgnore
    private String currentLoggedUser;

    @JsonCreator
    public CreateActionCommand(
            @JsonProperty("actionTypeId") Long actionTypeId,
            @JsonProperty("deviceId") List<String> deviceId,
            @JsonProperty("additionalParams") String additionalParams) {
        this.actionTypeId = actionTypeId;
        this.deviceId = deviceId;
        this.additionalParams = additionalParams;
    }

    public Long getActionTypeId() {
        return actionTypeId;
    }

    public List<String> getDeviceId() {
        return deviceId;
    }

    public String getAdditionalParams() {
        return additionalParams;
    }

    @JsonIgnore
    public String getCurrentLoggedUserLogin() {
        return currentLoggedUser;
    }
    @JsonIgnore
    public void setCurrentLoggedUser(String currentLoggedUser) {
        this.currentLoggedUser = currentLoggedUser;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return CreateActionHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
