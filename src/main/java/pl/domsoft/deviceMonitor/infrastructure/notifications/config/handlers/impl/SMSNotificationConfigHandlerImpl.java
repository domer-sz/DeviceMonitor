package pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.impl;

import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SMSNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces.SMSNotificationConfigHandler;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories.SMSNotificationRepository;

/**
 * Created by szymo on 22.08.2017.
 * Handler zapisujący w bazie danych konfiguację powiadomień email
 */
@Component
class SMSNotificationConfigHandlerImpl implements SMSNotificationConfigHandler {

    private final SMSNotificationRepository smsNotificationRepository;

    SMSNotificationConfigHandlerImpl(SMSNotificationRepository smsNotificationRepository) {
        this.smsNotificationRepository = smsNotificationRepository;
    }

    @Override
    public Void handle(SMSNotificationsConfigCommand command) throws AppException {
        smsNotificationRepository.deleteAll();
        smsNotificationRepository.save(command.toEntity());
        return null;
    }
}
