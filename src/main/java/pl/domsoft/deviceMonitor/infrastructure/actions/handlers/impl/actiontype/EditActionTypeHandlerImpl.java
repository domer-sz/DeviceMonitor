package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.impl.actiontype;

import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype.EditActionTypeCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionType;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actiontype.EditActionTypeHandler;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionTypeRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;

/**
 * Created by szymo on 18.06.2017.
 */
@Component
class EditActionTypeHandlerImpl implements EditActionTypeHandler{

    private final ActionTypeRepository actionTypeRepository;

    EditActionTypeHandlerImpl(ActionTypeRepository actionTypeRepository) {
        this.actionTypeRepository = actionTypeRepository;
    }

    @Override
    public ActionType handle(EditActionTypeCommand command) throws AppException {
        final ActionType editedActionType = actionTypeRepository.findOne(command.getId());
        editedActionType.setContent(command.getContent());
        editedActionType.setName(command.getName());
        actionTypeRepository.save(editedActionType);
        return editedActionType;
    }

}
