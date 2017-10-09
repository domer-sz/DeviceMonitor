package pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.DeleteAccountCommand;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;
import pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.interfaces.DeleteAccountCommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;

/**
 * Created by szymo on 11.06.2017.
 */
@Component
class DeleteAccountCommandHandlerImpl implements DeleteAccountCommandHandler, ExceptionPersistable  {

    private final QueryAccountRepository queryAccountRepository;
    private final AppExceptionRepository appExceptionRepository;

    DeleteAccountCommandHandlerImpl(QueryAccountRepository queryAccountRepository, AppExceptionRepository appExceptionRepository) {
        this.queryAccountRepository = queryAccountRepository;
        this.appExceptionRepository = appExceptionRepository;
    }

    @Override
    public Void handle(DeleteAccountCommand command) throws AppException {
        final Account toDelete = queryAccountRepository.findOne(command.getIdToDelete());
        if(toDelete.getRole().equals(UserRole.ADMIN)){
            throw new AppException(
                    "Nie możesz usunąc konta administratora",
                    "Nie możesz usunąc konta administratora",
                    HttpStatus.CONFLICT, getExcpRep()
            );
        }
        queryAccountRepository.delete(toDelete);
        return null;
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
