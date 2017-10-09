package pl.domsoft.deviceMonitor.infrastructure.user.account.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.interfaces.ChangeAccountActivityCommandHandler;

/**
 * Created by szymo on 25.07.2017.
 */
public class ChangeAccountActivityCommand implements Command{

    private final Long accountId;
    private final boolean accountActive;

    @JsonCreator
    public ChangeAccountActivityCommand(
    @JsonProperty("accountId") Long accountId,
    @JsonProperty("accountActive") boolean accountActive
    ) {
        this.accountId = accountId;
        this.accountActive = accountActive;
    }

    public Long getAccountId() {
        return accountId;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return ChangeAccountActivityCommandHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
