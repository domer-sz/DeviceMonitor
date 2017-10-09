package pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.email.commands.EmailCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendEmailNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces.SendEmailNotificationHandler;

import javax.mail.internet.MimeMessage;

/**
 * Created by szymo on 26.08.2017.
 */
@Component
class SendEmailNotificationHandlerImpl implements SendEmailNotificationHandler {

    private final CommandBus commandBus;
    private final DeviceConfigRepository deviceConfigRepository;
    private final JavaMailSender javaMailSender;


    SendEmailNotificationHandlerImpl(CommandBus commandBus, DeviceConfigRepository deviceConfigRepository, JavaMailSender javaMailSender) {
        this.commandBus = commandBus;
        this.deviceConfigRepository = deviceConfigRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Boolean handle(SendEmailNotificationCommand command) throws AppException {
        final MimeMessage mimeMessage = command.updateEmail(javaMailSender.createMimeMessage(), deviceConfigRepository);

        return commandBus.sendCommand(new EmailCommand(mimeMessage),Boolean.class);
    }
}
