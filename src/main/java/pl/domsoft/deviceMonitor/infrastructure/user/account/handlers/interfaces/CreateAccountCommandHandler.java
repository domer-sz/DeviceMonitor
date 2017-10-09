package pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.CreateAccountCommand;

/**
 * Created by szymo on 11.06.2017.
 */
public interface CreateAccountCommandHandler extends CommandHandler<CreateAccountCommand, Long> {
}
