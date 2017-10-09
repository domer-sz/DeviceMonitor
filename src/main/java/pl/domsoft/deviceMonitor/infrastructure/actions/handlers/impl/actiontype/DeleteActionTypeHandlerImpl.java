package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.impl.actiontype;

import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype.DeleteActionTypeCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actiontype.DeleteActionTypeHandler;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionTypeRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;

/**
 * Created by szymo on 18.06.2017.
 */
@Component
class DeleteActionTypeHandlerImpl implements DeleteActionTypeHandler{

    private final ActionTypeRepository actionTypeRepository;

    DeleteActionTypeHandlerImpl(ActionTypeRepository actionTypeRepository) {
        this.actionTypeRepository = actionTypeRepository;
    }

    @Override
    public Void handle(DeleteActionTypeCommand command) throws AppException {
        actionTypeRepository.delete(command.getId());
        return null;
    }
}
