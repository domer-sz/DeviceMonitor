package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actionlog;

import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actionlog.ActionLogCreatorCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionLog;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;

import java.util.List;

/**
 * Created by szymo on 12.07.2017.
 */
public interface CreateActionLogHandler extends CommandHandler<ActionLogCreatorCommand, Void>{
}
