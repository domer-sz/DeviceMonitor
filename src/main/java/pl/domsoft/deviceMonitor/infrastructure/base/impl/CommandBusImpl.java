package pl.domsoft.deviceMonitor.infrastructure.base.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandlerFactory;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;

/**
 * Created by szymo on 22.04.2017.
 * Bean będący szyną komend
 */
@Service
class CommandBusImpl implements CommandBus, ExceptionPersistable {
    private static final Logger log = LoggerFactory.getLogger(CommandBusImpl.class);
    private final CommandHandlerFactory commandHandlerFactory;
    private final AppExceptionRepository appExceptionRepository;

    public CommandBusImpl(CommandHandlerFactory commandHandlerFactory, AppExceptionRepository appExceptionRepository) {
        this.commandHandlerFactory = commandHandlerFactory;
        this.appExceptionRepository = appExceptionRepository;
    }

    /**
     * Metoda wysyłą komende do dopowiedniego handlera, który ją wykonuje
     * @param command Komenda która będzie wykonywana
     * @param clazz Klasa będąca odpowiedzią handlera, musi być zgodna z faktycznym typem zwracanym przez handler,
     *              gdy tak nie jest zostanie zwrócony wyjątek {@link AppException}
     *              Gdy handler nic nie zwraca należy przekazać tutaj null
     * @param <T> - typ komendy, musi implementować {@link Command}
     * @param <TOutput> - typ odpowiedź
     * @return odpowiedź o typie <TOutput> zwrócona przez handler
     */
    @Override
    public <T extends Command, TOutput> TOutput sendCommand(T command, Class<TOutput> clazz) throws AppException {

        if(command.isLoggable()){
            log.info("Przesyłam komende: " + command.getClass().getName(), command);
            log.info("Pobieram handlera dla komendy " + command.getClass());
            log.info("Wykonuje komende " + command);
        }
        CommandHandler commandHandler = commandHandlerFactory.getHandler(command);
        Object handlerOutput = commandHandler.handle(command);
        if(clazz != null && clazz != Void.class && handlerOutput != null){
            try {
                if(command.isLoggable()) {
                    log.info("Komenda wykonana, handler zwrócił odpowiedź " , handlerOutput);
                }
                return (TOutput) handlerOutput;
            }catch (ClassCastException e){
                throw new AppException("Błąd rzutowania odpowiedzi handlera", e, getExcpRep());
            }
        }else{
            if(command.isLoggable()) {
                log.info("Komenda wykonana, handler nie zwrócił odpowiedzi");
            }
            return null;
        }

    }

    /**
     * Metoda wysyłą komende do dopowiedniego handlera, który ją wykonuje, ta metoda ignoruję odpowiedź od handlera
     * @param comand Komenda która będzie wykonywana
     * @param <T> - typ komendy, musi implementować {@link Command}
     */
    @Override
    public <T extends Command> void sendCommand(T comand) throws AppException {
        sendCommand(comand, null);
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
