package pl.domsoft.deviceMonitor.infrastructure.user.account.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.interfaces.DeleteAccountCommandHandler;

/**
 * Created by szymo on 11.06.2017.
 */
public class DeleteAccountCommand implements Command {

    private final long idToDelete;

    private DeleteAccountCommand(){
        idToDelete = 0;
    }

    @JsonCreator
    public DeleteAccountCommand(
            @JsonProperty("idToDelete") long idToDelete
    ) {
        this.idToDelete = idToDelete;
    }

    public long getIdToDelete() {
        return idToDelete;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return DeleteAccountCommandHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
