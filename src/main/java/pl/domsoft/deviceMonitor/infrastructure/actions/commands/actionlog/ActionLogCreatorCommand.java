package pl.domsoft.deviceMonitor.infrastructure.actions.commands.actionlog;

import pl.domsoft.deviceMonitor.infrastructure.actions.commands.action.CreateActionCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actionlog.CreateActionLogHandler;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;

/**
 * Created by szymo on 11.07.2017.
 */
public class ActionLogCreatorCommand implements Command{

    private final CreateActionCommand createActionCommand;

    public ActionLogCreatorCommand(CreateActionCommand createActionCommand) {
        this.createActionCommand = createActionCommand;
    }

    public CreateActionCommand getCreateActionCommand() {
        return createActionCommand;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return CreateActionLogHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
