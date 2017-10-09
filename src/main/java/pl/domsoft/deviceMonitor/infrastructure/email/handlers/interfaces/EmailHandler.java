package pl.domsoft.deviceMonitor.infrastructure.email.handlers.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.email.commands.EmailCommand;

/**
 * Created by szymo on 26.08.2017.
 */
public interface EmailHandler extends CommandHandler<EmailCommand, Boolean> {
}
