package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actiontype;

import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype.EditActionTypeCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionType;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;

/**
 * Created by szymo on 18.06.2017.
 */
public interface EditActionTypeHandler extends CommandHandler<EditActionTypeCommand, ActionType> {
}
