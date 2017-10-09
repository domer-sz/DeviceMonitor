package pl.domsoft.deviceMonitor.infrastructure.base.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;

/**
 * Created by szymo on 22.04.2017.
 * Interfejs fabryki commandHandlerów
 */
public interface CommandHandlerFactory {

    /**
     * Przyjmuje obiekt komendy, i zwraca handler dla niej zarejestrowany
     * @param command - Obiekt komendy
     * @return - implementacja handlera dla danej komendy
     */
    CommandHandler getHandler(Command command) throws AppException;


}
