package pl.domsoft.deviceMonitor.application.endpoints.rest.actions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype.CreateActionTypeCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype.DeleteActionTypeCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype.EditActionTypeCommand;
import pl.domsoft.deviceMonitor.infrastructure.actions.entities.ActionType;
import pl.domsoft.deviceMonitor.infrastructure.actions.repositories.ActionTypeRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;

import java.util.Collection;

/**
 * Created by szymo on 10.07.2017.
 * Endpoint zarządzający typami akcji
 */
@RestController
@RequestMapping(path = "/api/actions/types")
@CrossOrigin(origins = "*")
public class ActionTypeController implements ExceptionPersistable{

    private final CommandBus commandBus;
    private final ActionTypeRepository actionTypeRepository;
    private final AppExceptionRepository appExceptionRepository;

    public ActionTypeController(CommandBus commandBus, ActionTypeRepository actionTypeRepository, AppExceptionRepository appExceptionRepository) {
        this.commandBus = commandBus;
        this.actionTypeRepository = actionTypeRepository;
        this.appExceptionRepository = appExceptionRepository;
    }

    @GetMapping("")
    public Collection<ActionType> findAllActionTypes(){
        return actionTypeRepository.findAll();
    }

    /**
     * Pobiera stronę z wpisami o awariach urządzeń
     * @param pageable
     * @return
     */
    @GetMapping("/pageable")
    public Page<ActionType> findAllActionTypes(Pageable pageable){
        return actionTypeRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ActionType findOneActionType(@PathVariable(name = "id") Long id) throws AppException {
        if(id != null){
            return actionTypeRepository.findOne(id);
        }else throw new AppException("Przekazane id nie mże bć nullem", getExcpRep());
    }

    @PostMapping("")
    public ActionType createActionType(@RequestBody CreateActionTypeCommand createActionTypeCommand) throws AppException {
        return commandBus.sendCommand(createActionTypeCommand, ActionType.class);
    }

    @PutMapping("")
    public ActionType editActionType(@RequestBody EditActionTypeCommand editActionTypeCommand) throws AppException {
        return commandBus.sendCommand(editActionTypeCommand, ActionType.class);
    }

    @DeleteMapping("/{id}")
    public String deleteActionType(@PathVariable(name = "id") Long id ) throws AppException {
        commandBus.sendCommand(new DeleteActionTypeCommand(id));
        return "{\"text\" : \"Poprawnie usunięto\"}";
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
