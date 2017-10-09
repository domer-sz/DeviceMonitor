package pl.domsoft.deviceMonitor.infrastructure.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandlerFactory;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;

/**
 * Created by szymo on 22.04.2017.
 * Implementacja fabryki handlerów
 */
@Service
public class CommandHandlerFactoryImpl implements CommandHandlerFactory , ExceptionPersistable{

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AppExceptionRepository appExceptionRepository;

    @Override
    public CommandHandler getHandler(Command command) throws AppException{
        Class<? extends CommandHandler> handlerClass = command.getHandlerClass();
        final CommandHandler handlerBean = applicationContext.getBean(handlerClass);
        if(handlerBean == null) throw new AppException("Nie znaleziono handlera dla komendy: " + command.getClass(), getExcpRep());
        return handlerBean;
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
