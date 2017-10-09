package pl.domsoft.deviceMonitor.application.endpoints.rest.actions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.action.CreateActionCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.Action;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.AuthenticationUtil;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by szymo on 11.07.2017.
 * Controller do wysyłąnia akcji i wyśiwetlania wysłanych akcji
 */
@RestController
@RequestMapping(path = "/api/actions")
@CrossOrigin(origins = "*")
public class ActionController implements ExceptionPersistable{

    private final CommandBus commandBus;
    private final ActionRepository actionRepository;
    private final AppExceptionRepository appExceptionRepository;

    public ActionController(CommandBus commandBus, ActionRepository actionRepository, AppExceptionRepository appExceptionRepository) {
        this.commandBus = commandBus;
        this.actionRepository = actionRepository;
        this.appExceptionRepository = appExceptionRepository;
    }

    @GetMapping("")
    public Collection<Action> findAllActions(){
        return actionRepository.findAll();
    }

    /**
     * Pobiera stronę z wpisami o awariach urządzeń
     * @param pageable
     * @return
     */
    @GetMapping("/pageable")
    public Page<Action> findAllActions(Pageable pageable){
        return actionRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Action findOneAction(@PathVariable(name = "id") Long id) throws AppException {
        if(id != null){
            return actionRepository.findOne(id);
        }else throw new AppException("Przekazane id nie mże bć nullem", getExcpRep());
    }

    @PostMapping("")
    public HashMap<String, String> createActionType(@RequestBody CreateActionCommand createActionCommand) throws AppException {
        final Authentication currentAuthentication = AuthenticationUtil.getCurrentAuthentication();
        createActionCommand.setCurrentLoggedUser(currentAuthentication.getName());
        commandBus.sendCommand(createActionCommand);
        HashMap<String, String> map = new HashMap<>();
        map.put("status", "ok");
        return map;
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
