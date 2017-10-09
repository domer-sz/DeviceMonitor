package pl.domsoft.deviceMonitor.infrastructure.actions.handlers.impl.actionlog;

import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actionlog.ActionLogCreatorCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionLog;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionType;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actionlog.CreateActionLogHandler;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionTypeRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by szymo on 12.07.2017.
 */
@Component
class CreateActionLogHandlerImpl implements CreateActionLogHandler {

    private final ActionTypeRepository actionTypeRepository;
    private final ActionLogRepository actionLogRepository;

    CreateActionLogHandlerImpl(ActionTypeRepository actionTypeRepository, ActionLogRepository actionLogRepository) {
        this.actionTypeRepository = actionTypeRepository;
        this.actionLogRepository = actionLogRepository;
    }

    @Override
    public Void handle(ActionLogCreatorCommand command) throws AppException {
        List<ActionLog> logsToSave = new ArrayList<>();
        for (String deviceId : command.getCreateActionCommand().getDeviceId()) {
                try {
                    ActionLog actionLog = new ActionLog();
                    actionLog.setSendingUsername(command.getCreateActionCommand().getCurrentLoggedUserLogin());
                    final ActionType type = actionTypeRepository.findOne(command.getCreateActionCommand().getActionTypeId());
                    actionLog.setSentActionName(type.getName());
                    actionLog.setSentContent(type.getContent());
                    actionLog.setSentDate(new Date());
                    actionLog.setDeviceId(deviceId);
                    logsToSave.add(actionLog);
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
        actionLogRepository.save(logsToSave);
        return null;
    }

}
