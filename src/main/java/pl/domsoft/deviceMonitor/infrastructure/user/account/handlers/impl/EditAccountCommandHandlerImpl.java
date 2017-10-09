package pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.EditAccountCommand;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;
import pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.interfaces.EditAccountCommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;

/**
 * Created by szymo on 11.06.2017.
 */
@Component
class EditAccountCommandHandlerImpl implements EditAccountCommandHandler, ExceptionPersistable {

    private final QueryAccountRepository queryAccountRepository;
    private final AppExceptionRepository appExceptionRepository;

    public EditAccountCommandHandlerImpl(QueryAccountRepository queryAccountRepository, AppExceptionRepository appExceptionRepository) {
        this.queryAccountRepository = queryAccountRepository;
        this.appExceptionRepository = appExceptionRepository;
    }

    @Override
    public Void handle(EditAccountCommand command) throws AppException {
        final Account edited = queryAccountRepository.findOne(command.getId());
        if(command.getPassword1() != null){
            if(!command.getPassword1().equals(command.getPassword2())) throw
                    new AppException(
                            "Wybrane hasła nie są takie same",
                            "Wybrane hasła nie są takie same",
                            HttpStatus.BAD_REQUEST, getExcpRep()
                    );
            if(command.getPassword1().length() < 6) throw
                    new AppException("Hasło musi mieć co najmniej 6 znaków",
                            "Hasło musi mieć co najmniej 6 znaków, wybierz dłuższe hasło",
                            HttpStatus.BAD_REQUEST, getExcpRep());
            final BCryptPasswordEncoder coder = new BCryptPasswordEncoder();
            edited.setPassword(coder.encode(command.getPassword1()));
        }
        if(command.getRole().equals(UserRole.ADMIN)) throw
                new AppException("Nie możesz utworzyć konta administratora",
                        "Nie możesz utworzyć kolejnego konta administratora",
                        HttpStatus.BAD_REQUEST, getExcpRep());
        edited.setActive(command.isActive() == null ? true : command.isActive());
        edited.setRole(command.getRole());
        queryAccountRepository.save(edited);
        return null;
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
