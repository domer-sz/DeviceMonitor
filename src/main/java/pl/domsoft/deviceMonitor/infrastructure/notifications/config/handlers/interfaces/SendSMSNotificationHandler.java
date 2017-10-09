package pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendEmailNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendSMSNotificationCommand;

/**
 * Created by szymo on 22.08.2017.
 */
public interface SendSMSNotificationHandler extends CommandHandler<SendSMSNotificationCommand, Boolean> {
}
