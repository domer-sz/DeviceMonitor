package pl.domsoft.deviceMonitor.infrastructure.user.account.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.interfaces.CreateAccountCommandHandler;

/**
 * Created by szymo on 11.06.2017.
 * Komenda tworzenia konta użytkownika
 */
public class CreateAccountCommand implements Command{

    private final String login;
    private final String password1;
    private final String password2;
    private final UserRole role;
    private final boolean active;

    private CreateAccountCommand(){
        active = false;
        role = null;
        password2 = null;
        password1 = null;
        login = null;
    }

    @JsonCreator
    public CreateAccountCommand(
            @JsonProperty("login") String login,
            @JsonProperty("password1") String password1,
            @JsonProperty("password2") String password2,
            @JsonProperty("role") UserRole role,
            @JsonProperty("active") boolean active
    ) {
        this.login = login;
        this.password1 = password1;
        this.password2 = password2;
        this.role = role;
        this.active = active;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return CreateAccountCommandHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return false;
    }
}
