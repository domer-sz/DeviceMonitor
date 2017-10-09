package pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.EmailNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailNotificationConfig;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces.EmailNotificationConfigHandler;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories.EmailNotificationRepository;

import java.util.Collection;

/**
 * Created by szymo on 22.08.2017.
 * Handler zapisujący w bazie danych konfiguację powiadomień email
 */
@Component
class EmailNotificationConfigHandlerImpl implements EmailNotificationConfigHandler {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Override
    public Void handle(EmailNotificationsConfigCommand command) throws AppException {
        System.out.println(command);
        emailNotificationRepository.deleteAll();
        emailNotificationRepository.save(command.toEntity());
        return null;
    }
}
