package pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.impl;

import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendEmailNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendSMSNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces.SendSMSNotificationHandler;

/**
 * Created by szymo on 26.08.2017.
 */
@Component
class SendSMSNotificationHandlerImpl implements SendSMSNotificationHandler {


    private final DeviceConfigRepository deviceConfigRepository;
    private final CommandBus commandBus;

    SendSMSNotificationHandlerImpl(DeviceConfigRepository deviceConfigRepository, CommandBus commandBus) {
        this.deviceConfigRepository = deviceConfigRepository;
        this.commandBus = commandBus;
    }

    @Override
    public Boolean handle(SendSMSNotificationCommand command) throws AppException {
        final Object o = command.updateSms(deviceConfigRepository);
//        dziabadziabadziaba
        return false;
    }
}
