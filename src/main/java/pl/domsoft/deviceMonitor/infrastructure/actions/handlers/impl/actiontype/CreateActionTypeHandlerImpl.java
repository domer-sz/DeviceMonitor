package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.impl.actiontype;

import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype.CreateActionTypeCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionType;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actiontype.CreateActionTypeHandler;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionTypeRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;

/**
 * Created by szymo on 18.06.2017.
 */
@Component
class CreateActionTypeHandlerImpl implements CreateActionTypeHandler{

    private final ActionTypeRepository actionTypeRepository;

    CreateActionTypeHandlerImpl(ActionTypeRepository actionTypeRepository) {
        this.actionTypeRepository = actionTypeRepository;
    }

    @Override
    public ActionType handle(CreateActionTypeCommand command) throws AppException {
        ActionType actionType = new ActionType(command.getName(), command.getContent());
        actionTypeRepository.save(actionType);
        return actionType;
    }
}
