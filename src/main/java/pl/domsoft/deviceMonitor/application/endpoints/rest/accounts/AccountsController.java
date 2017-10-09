package pl.domsoft.deviceMonitor.application.endpoints.rest.accounts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.ChangeAccountActivityCommand;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.CreateAccountCommand;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.DeleteAccountCommand;
import pl.domsoft.deviceMonitor.infrastructure.user.account.commands.EditAccountCommand;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;
import pl.domsoft.deviceMonitor.infrastructure.user.account.model.AccountDetailsViewModel;
import pl.domsoft.deviceMonitor.infrastructure.user.account.model.AccountTableViewModel;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szymo on 11.06.2017.
 * Kontroler do zarządzania użytownikami
 */
@RestController
@RequestMapping(path = "/api/admin/accounts")
@CrossOrigin(origins = "*")
public class AccountsController {

    private final QueryAccountRepository queryAccountRepository;
    private final CommandBus commandBus;

    public AccountsController(QueryAccountRepository queryAccountRepository, CommandBus commandBus) {
        this.queryAccountRepository = queryAccountRepository;
        this.commandBus = commandBus;
    }

    /**
     * Pobiera stronę z kontami
     * @param pageable
     * @return
     */
    @GetMapping()
    public Page<AccountTableViewModel> getAccounts(Pageable pageable){
        return queryAccountRepository.findAllConverted(pageable);
    }

    /**
     * Pobiera szczegóły wybranego konta użytownika
     * @param accountId
     * @return
     * @throws AppException
     */
    @GetMapping("/{accountId}")
    public AccountDetailsViewModel getAccountDetails(@PathVariable("accountId") long accountId) throws AppException {
        return queryAccountRepository.findOneConverted(accountId);
    }

    /**
     * Edytuje wybrane konto użytkownika
     * @param command
     * @return
     * @throws AppException
     */
    @PutMapping()
    public Map<String, String> editAccount(@RequestBody EditAccountCommand  command) throws AppException {
        commandBus.sendCommand(command);
        return buildOkResponse();
    }

    /**
     * Aktywuje lub deaktywuje wybrane konto użytkownika, nie można zmieniać aktywności administratora
     * @param command
     * @return
     * @throws AppException
     */
    @PutMapping("/activity")
    public Map<String, String> changeActivityAccount(@RequestBody ChangeAccountActivityCommand command) throws AppException {
        commandBus.sendCommand(command);
        return buildOkResponse();
    }

    /**
     * Tworzy nowe konto użytkownika
     * @param command
     * @return
     * @throws AppException
     */
    @PostMapping()
    public Long createAccount(@RequestBody CreateAccountCommand command) throws AppException {
        return commandBus.sendCommand(command, Long.class);
    }

    /**
     * Sprawdza czy wybrany login jest dostępny
     * @param login
     * @return
     */
    @GetMapping("/{login}/checkAvailablity")
    public boolean checkLoginAvailablity(@PathVariable("login") String login){
        final Account byLogin = queryAccountRepository.findByLogin(login);
        return byLogin != null;
    }

    @DeleteMapping("/{accountId}")
    public Map<String,String> deleteAccount(@PathVariable("accountId") long accountId) throws AppException {
        commandBus.sendCommand(new DeleteAccountCommand(accountId));
        return buildOkResponse();
    }

    @GetMapping("/roles")
    public List<Map.Entry<Long, String>> getUserRoles(){
        return UserRole.getAllEnumValues();
    }

    private Map<String,String> buildOkResponse() {
        Map<String,String> responseObject = new LinkedHashMap<>();
        responseObject.put("status", "ok");
        return responseObject;
    }


}
