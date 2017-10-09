package pl.domsoft.deviceMonitor.infrastructure.actions.entities;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by szymo on 15.06.2017.
 * Logi wykonywanych akcji
 */
@Entity
@Table(name = "action_log")
public class ActionLog extends BaseEntity{


    /**
     * wyśiwetlana nazwa akcji
     */
    @Column(name = "sent_action_name")
    private String sentActionName;
    /**
      * Zawartość będąca faktyczną definicją akcji, to co tutaj się wpiszę będzie wysłane
      * do urządzenie i urządzenie podejmie próbę ykonania taj akcji
     */
    @Column(name = "sent_content", columnDefinition = "VARCHAR(10000)")
    private String sentContent;

    /**
     * login użytkownika wysyłającego akcje
     */
    @Column(name = "sending_user")
    private String sendingUsername;

    /**
     * Data wysłania akcji
     */
    @Column(name = "send_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;

    /**
     * id urządzenia do którego akcja zostałą wysłana
     */
    @Column(name = "device_id")
    private String deviceId;

    public ActionLog() {
    }

    public String getSentActionName() {
        return sentActionName;
    }

    public void setSentActionName(String sentActionName) {
        this.sentActionName = sentActionName;
    }

    public String getSentContent() {
        return sentContent;
    }

    public void setSentContent(String sentContent) {
        this.sentContent = sentContent;
    }

    public String getSendingUsername() {
        return sendingUsername;
    }

    public void setSendingUsername(String sendingUsername) {
        this.sendingUsername = sendingUsername;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
