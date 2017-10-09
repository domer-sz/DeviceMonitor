package pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actiontype.DeleteActionTypeHandler;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;

/**
 * Created by szymo on 18.06.2017.
 * Komenda usuwająca typ akcji
 */
public class DeleteActionTypeCommand implements Command{
    /**
     * Id edytowanej encji
     */
    private final Long id;

    @JsonCreator
    public DeleteActionTypeCommand(
            @JsonProperty("id") Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return DeleteActionTypeHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
