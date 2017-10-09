package pl.domsoft.deviceMonitor.infrastructure.actions.commands.actiontype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.actions.handlers.interfaces.actiontype.EditActionTypeHandler;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;

/**
 * Created by szymo on 18.06.2017.
 * Komenda edytująca typ akcji
 */
public class EditActionTypeCommand implements Command{
    /**
     * Id edytowanej encji
     */
    private final Long id;
    /**
     * Nazwa tworzonej akcji
     */
    private final String name;
    /**
     * "zawartosc" akjci, ciag znakow wysylany do urzadzenia w cleu wykonania akcji
     */
    private final String content;

    @JsonCreator
    public EditActionTypeCommand(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("content") String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return EditActionTypeHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
