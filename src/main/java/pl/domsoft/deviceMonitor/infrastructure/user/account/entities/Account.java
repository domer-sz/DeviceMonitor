package pl.domsoft.deviceMonitor.infrastructure.user.account.entities;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by szymo on 09.06.2017.
 * Encja konta użytkownika
 */
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "active")
    private boolean active;

    public Account() {
    }

    public Account(String login, String password, UserRole role, Date createTime, boolean active) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.createTime = createTime;
        this.active = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
