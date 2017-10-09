package pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.CreateAccountCommand;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;
import pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.interfaces.CreateAccountCommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;

import java.util.Date;

/**
 * Created by szymo on 11.06.2017.
 */
@Component
class CreateAccountCommandHandlerImpl implements CreateAccountCommandHandler, ExceptionPersistable {

    private final QueryAccountRepository queryAccountRepository;
    private final AppExceptionRepository appExceptionRepository;

    CreateAccountCommandHandlerImpl(QueryAccountRepository queryAccountRepository, AppExceptionRepository appExceptionRepository) {
        this.queryAccountRepository = queryAccountRepository;
        this.appExceptionRepository = appExceptionRepository;
    }

    @Override
    public Long handle(CreateAccountCommand command) throws AppException {
        validateCommand(command);
        Account newAccount = new Account(
                command.getLogin(),
                command.getPassword1(),
                command.getRole(),
                new Date(),
                true
        );
        queryAccountRepository.save(newAccount);
        return newAccount.getId();
    }

    private void validateCommand(CreateAccountCommand command) throws AppException {
        if(!command.getPassword1().equals(command.getPassword2())) throw
                new AppException(
                        "Wybrane hasła nie są takie same",
                        "Wybrane hasła nie są takie same",
                        HttpStatus.BAD_REQUEST, getExcpRep()
                );
        if(queryAccountRepository.findByLogin(command.getLogin()) != null) throw
                    new AppException("Użytkownik z wybranym loginem już istnieje",
                    "Użytkownik z wybranym loginem już istnieje, wybież inny login",
                    HttpStatus.BAD_REQUEST, getExcpRep());
        if(command.getPassword1().length() < 6) throw
                new AppException("Hasło musi mieć co najmniej 6 znaków",
                        "Hasło musi mieć co najmniej 6 znaków, wybierz dłuższe hasło",
                        HttpStatus.BAD_REQUEST, getExcpRep());
        if(command.getRole().equals(UserRole.ADMIN)) throw
                new AppException("Nie możesz utworzyć konta administratora",
                        "Nie możesz utworzyć kolejnego konta administratora",
                        HttpStatus.BAD_REQUEST, getExcpRep());
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
