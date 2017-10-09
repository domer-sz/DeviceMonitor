package pl.domsoft.deviceMonitor.infrastructure.user.account.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;

import java.util.Date;

/**
 * Created by szymo on 11.06.2017.
 * Widok szczegółowy
 */
public class AccountDetailsViewModel {

    private final Long id;
    private final String login;
    private final String password1;
    private final String password2;
    private final UserRole role;
    private final boolean active;
    private final Date createTime;

    private AccountDetailsViewModel(){
        id = null;
        login = null;
        createTime = null;
        active = false;
        role = null;
        password2 = null;
        password1 = null;
    }

    @JsonCreator
    public AccountDetailsViewModel(
            @JsonProperty("id") Long id,
            @JsonProperty("login") String login,
            @JsonProperty("password1") String password1,
            @JsonProperty("password2") String password2,
            @JsonProperty("role") UserRole role,
            @JsonProperty("active") boolean active,
            @JsonProperty("createTime") Date createTime) {
        this.id = id;
        this.login = login;
        this.password1 = password1;
        this.password2 = password2;
        this.role = role;
        this.active = active;
        this.createTime = createTime;
    }

    public AccountDetailsViewModel(Account a) {
        this.id = a.getId();
        this.login = a.getLogin();
        this.role = a.getRole();
        this.active = a.isActive();
        this.createTime = a.getCreateTime();
        this.password1 = null;
        this.password2 = null;
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

    public boolean isActive() {
        return active;
    }

    public String getLogin() {
        return login;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
