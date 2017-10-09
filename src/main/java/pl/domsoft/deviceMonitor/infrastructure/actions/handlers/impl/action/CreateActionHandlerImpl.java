package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.impl.action;

import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.action.CreateActionCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actionlog.ActionLogCreatorCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.Action;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionType;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.action.CreateActionHandler;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionRepository;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionTypeRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szymo on 18.06.2017.
 */
@Component
class CreateActionHandlerImpl implements CreateActionHandler {

    private final ActionRepository actionRepository;
    private final ActionTypeRepository actionTypeRepository;
    private final QueryAccountRepository accountRepository;
    private final CommandBus commandBus;

    CreateActionHandlerImpl(ActionRepository actionRepository, ActionTypeRepository actionTypeRepository, QueryAccountRepository accountRepository, CommandBus commandBus) {
        this.actionRepository = actionRepository;
        this.actionTypeRepository = actionTypeRepository;
        this.accountRepository = accountRepository;
        this.commandBus = commandBus;
    }

    @Override
    public Void handle(CreateActionCommand command) throws AppException {
        List<Action> actionsToSend = buildActionEntities(command);
        actionRepository.save(actionsToSend);
        try {
            commandBus.sendCommand(new ActionLogCreatorCommand(command));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private List<Action> buildActionEntities(CreateActionCommand command) {
        List<Action> actionsToSend = new ArrayList<>();
        for ( String deviceId : command.getDeviceId()) {
            DataFromActionType dataFromActionType = new DataFromActionType(command).invoke();
            Action action = new Action();
            action.setName(dataFromActionType.getName());
            action.setContent(dataFromActionType.getContent());
            action.setAdditionalParams(command.getAdditionalParams());
            action.setCreateTime(DateTimeUtils.getCurrentDateTime());
            action.setExecuted(false);
            action.setDeviceId(deviceId);
            action.setSendingAccount(accountRepository.findByLogin(command.getCurrentLoggedUserLogin()));
            actionsToSend.add(action);
        }
        return actionsToSend;
    }

    private class DataFromActionType {
        private CreateActionCommand command;
        private String name;
        private String content;

        DataFromActionType(CreateActionCommand command) {
            this.command = command;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public DataFromActionType invoke() {

            if(command.getActionTypeId() != null){
                final ActionType actionType = actionTypeRepository.findOne(command.getActionTypeId());
                name = actionType.getName();
                content = actionType.getContent();
            }else {
                name = "";
                content = "";
            }
            return this;
        }
    }
}
