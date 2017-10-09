package pl.domsoft.deviceMonitor.infrastructure.device.entities.events;


import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceEventType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by szymo on 02.05.2017.
 * Encja przechowująca informacje o zdarzeniach w działaniu urządzeń
 */
@Entity
@Table(name = "device_events")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EVENT_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class DeviceEvent extends BaseEntity {

    /**
     * Id urządzenia którego dotyczy zdarzenie
     */
    @Column(name = "DEVICE_ID")
    protected String deviceId;

    /**
     * Początek zdarzenia
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    protected Date startDate;

    /**
     * Koniec zdarzenia
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    protected Date endDate;

    /**
     * Miejsce na komentarz dla danej zdarzenia
     */
    protected String comment;

    @Column(name = "notification_sent")
    protected Boolean notificationSent = false;


    protected DeviceEvent() {
    }

    public DeviceEvent(String deviceId, Date startDate, Date endDate, String comment) {
        this.deviceId = deviceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
    }

    public DeviceEvent(String deviceId, Date startDate, Date endDate) {
        this.deviceId = deviceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = "";
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public abstract DeviceEventType getType();

    public boolean isNotificationSent() {
        if(notificationSent == null ) return false;
        return notificationSent;
    }

    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }

    public abstract String toTextLine(DeviceConfig deviceConfig);
}
