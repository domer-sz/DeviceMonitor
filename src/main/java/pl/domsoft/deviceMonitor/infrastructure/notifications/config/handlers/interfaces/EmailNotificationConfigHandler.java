package pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.EmailNotificationsConfigCommand;

/**
 * Created by szymo on 22.08.2017.
 */
public interface EmailNotificationConfigHandler extends CommandHandler<EmailNotificationsConfigCommand, Void> {
}
