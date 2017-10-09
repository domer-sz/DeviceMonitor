package pl.domsoft.deviceMonitor.infrastructure.base.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;

/**
 * Created by szymo on 22.04.2017.
 * Podstatowy interfejs handlerów.
 * Każdy handler musi być springowym beanem aby dało się go stworzyć poprzez fabrykę commandHandlerów.
 * <TComand> - argument metody wykonującej komende
 * <TOutput> typ zwracany przez handler
 */
public interface CommandHandler<TCommand extends Command, TOutput> {
    /**
     * Metoda wykonująca komende
     * @param command - argument, musi implementowac interfejs command
     * @return zwraca obiekt klasy <TOptput> przekazanej jako typ generyczny
     */
    TOutput handle(TCommand command) throws AppException;

}
