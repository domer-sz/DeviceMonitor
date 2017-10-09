package pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actiontype.CreateActionTypeHandler;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;

/**
 * Created by szymo on 18.06.2017.
 * Komenda tworząca typ akcji
 */
public class CreateActionTypeCommand implements Command{
    /**
     * Nazwa tworzonej akcji
     */
    private final String name;
    /**
     * "zawartosc" akjci, ciag znakow wysylany do urzadzenia w cleu wykonania akcji
     */
    private final String content;

    @JsonCreator
    public CreateActionTypeCommand(
            @JsonProperty("name") String name,
            @JsonProperty("content") String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return CreateActionTypeHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
