package pl.domsoft.deviceMonitor.infrastructure.base.interfaces;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by szymo on 22.04.2017.
 * podstawowy interfejs komend, marker zbierający komendy
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Command {
    Class<? extends CommandHandler> getHandlerClass();
    boolean isLoggable();
}
