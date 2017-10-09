package pl.domsoft.deviceMonitor.infrastructure.user.account.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;

import java.util.Date;

/**
 * Created by szymo on 11.06.2017.
 * widok tabelkowy konta użytkownika
 */
public class AccountTableViewModel {

    private final Long id;
    private final String login;
    private final UserRole role;
    private final boolean active;
    private final Date createTime;

    private AccountTableViewModel(){
        createTime = null;
        active = false;
        role = null;
        login = null;
        id = null;
    }

    @JsonCreator
    public AccountTableViewModel(
            @JsonProperty("id") Long id,
            @JsonProperty("login") String login,
            @JsonProperty("role") UserRole role,
            @JsonProperty("active") boolean active,
            @JsonProperty("createTime") Date createTime) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.active = active;
        this.createTime = createTime;
    }

    public AccountTableViewModel(Account a) {
        this.id = a.getId();
        this.login = a.getLogin();
        this.role = a.getRole();
        this.active = a.isActive();
        this.createTime = a.getCreateTime();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
