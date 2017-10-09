package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.action;

import pl.domsoft.deviceMonitor.infrastructure.actions.commands.action.CreateActionCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.Action;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;

import java.util.List;

/**
 * Created by szymo on 18.06.2017.
 */
public interface CreateActionHandler extends CommandHandler<CreateActionCommand, Void> {
}
