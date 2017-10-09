package pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.ChangeAccountActivityCommand;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;
import pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.interfaces.ChangeAccountActivityCommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;

/**
 * Created by szymo on 25.07.2017.
 */
@Component
class ChangeAccountActivityCommandHandlerImpl implements ChangeAccountActivityCommandHandler{

    @Autowired
    private QueryAccountRepository queryAccountRepository;

    @Override
    public Void handle(ChangeAccountActivityCommand command) throws AppException {
        final Account one = queryAccountRepository.findOne(command.getAccountId());
        if(one != null && !one.getRole().equals(UserRole.ADMIN)){
            changeActivity(one, command.isAccountActive());
            queryAccountRepository.save(one);
        }
        return null;
    }

    private void changeActivity(Account account, boolean activity){
        account.setActive(activity);
    }
}
