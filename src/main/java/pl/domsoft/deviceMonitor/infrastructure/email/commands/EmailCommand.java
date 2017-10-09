package pl.domsoft.deviceMonitor.infrastructure.email.commands;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.email.handlers.interfaces.EmailHandler;

import javax.mail.internet.MimeMessage;

/**
 * Created by szymo on 26.08.2017.
 */
public class EmailCommand implements Command {

    private final MimeMessage mimeMessage;

    public EmailCommand(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return EmailHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
