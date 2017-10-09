package pl.domsoft.deviceMonitor.infrastructure.email.handlers.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.email.commands.EmailCommand;
import pl.domsoft.deviceMonitor.infrastructure.email.handlers.interfaces.EmailHandler;

/**
 * Created by szymo on 26.08.2017.
 * Handler przyjmujący komende emaila i wysyłający go do odbiorcy
 */
@Component
class EmailHandlerImpl implements EmailHandler {

    private final CommandBus commandBus;

    private final JavaMailSender javaMailSender;

    EmailHandlerImpl(CommandBus commandBus, JavaMailSender javaMailSender) {
        this.commandBus = commandBus;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Boolean handle(EmailCommand command) throws AppException {
        try {
            javaMailSender.send(command.getMimeMessage());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
