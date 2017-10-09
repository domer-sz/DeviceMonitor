package pl.domsoft.deviceMonitor.infrastructure.user.account.commands;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.handlers.interfaces.EditAccountCommandHandler;

import java.util.Date;

/**
 * Created by szymo on 11.06.2017.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditAccountCommand implements Command{

    private  Long id;
    private  String login;
    private  String password1;
    private  String password2;
    private  UserRole role;
    private  Boolean active;
    private  Date createTime;

    private EditAccountCommand(){
    }

    @JsonCreator
    public EditAccountCommand(
            @JsonProperty("id") Long id,
            @JsonProperty("login") String login,
            @JsonProperty("password1") String password1,
            @JsonProperty("password2") String password2,
            @JsonProperty("role") long role,
            @JsonProperty("active") Boolean active,
            @JsonProperty("createTime") Date createTime) throws AppException {
        this.id = id;
        this.login = login;
        this.password1 = password1;
        this.password2 = password2;
        this.role = UserRole.creator(role);
        this.active = active;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
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

    public Boolean isActive() {
        return active;
    }

    public String getLogin() {
        return login;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return EditAccountCommandHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
