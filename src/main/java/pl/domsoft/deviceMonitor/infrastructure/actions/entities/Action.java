package pl.domsoft.deviceMonitor.infrastructure.actions.entities;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;
import pl.domsoft.deviceMonitor.infrastructure.user.account.entities.Account;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by szymo on 16.06.2017.
 * Instancja akcji
 */
@Entity
@Table(name = "action")
public class Action extends BaseEntity{

    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "execution_time")
    private Date executionTime;
    @Column(name = "executed")
    private boolean executed;
    /**
     * Być może się do czegoś przyda, jeszcze nie wiem dokładnie jak będą dzialać akcje
     */
    @Column(name = "additional_params", columnDefinition = "VARCHAR(4000)")
    private String additionalParams;

    @ManyToOne
    @JoinColumn(name = "sending_account")
    private Account sendingAccount;

    /**
     * nazwa przesyłanej akcji
     */
    @Column(name = "name")
    private String name;
    /**
     * zawartość przesyłanej akcji
     */
    @Column(name = "content", columnDefinition = "VARCHAR(10000)")
    private String content;

    public Action() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public String getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(String additionalParams) {
        this.additionalParams = additionalParams;
    }

    public Account getSendingAccount() {
        return sendingAccount;
    }

    public void setSendingAccount(Account sendingAccount) {
        this.sendingAccount = sendingAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
