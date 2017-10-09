package pl.domsoft.deviceMonitor.infrastructure.base.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;

/**
 * Created by szymo on 22.04.2017.
 * Interfejs szyny komend
 */
public interface CommandBus {
    /**
     * Metoda wysyłą komende do dopowiedniego handlera, który ją wykonuje
     * @param comand Komenda która będzie wykonywana
     * @param clazz Klasa będąca odpowiedzią handlera, musi być zgodna z faktycznym typem zwracanym przez handler,
     *              gdy tak nie jest zostanie zwrócony wyjątek {@link AppException}
     *              Gdy handler nic nie zwraca należy przekazać tutaj null
     * @param <T> - typ komendy, musi implementować {@link Command}
     * @param <TOutput> - typ odpowiedź
     * @return odpowiedź o typie <TOutput> zwrócona przez handler
     */
    <T extends Command, TOutput> TOutput sendCommand(T comand, Class<TOutput> clazz) throws AppException;

    /**
     * Metoda wysyłą komende do dopowiedniego handlera, który ją wykonuje, ta metoda ignoruję odpowiedź od handlera
     * @param comand Komenda która będzie wykonywana
     * @param <T> - typ komendy, musi implementować {@link Command}
     */
    <T extends Command> void sendCommand(T comand) throws AppException;
}
