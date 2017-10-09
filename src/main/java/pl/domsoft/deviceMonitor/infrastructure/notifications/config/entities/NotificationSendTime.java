package pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationSendTimeModel;

import javax.persistence.*;

/**
 * Created by szymo on 22.08.2017.
 */
@Entity
@Table(name = "notification_send_time")
public class NotificationSendTime extends BaseEntity{

    @Column(name = "hour")
    private String hour;


    @ManyToOne
    @JoinColumn(name = "config_id")
    private NotificationConfig notificationConfig;

    public NotificationSendTime() {
    }

    public NotificationSendTime(String hour) {
        this.hour = hour;
    }

    NotificationSendTime(NotificationSendTimeModel sendTimeModel, NotificationConfig notificationConfig) {
        this.setHour(sendTimeModel.getHour());
        this.notificationConfig = notificationConfig;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public NotificationConfig getNotificationConfig() {
        return notificationConfig;
    }

    public void setNotificationConfig(NotificationConfig notificationConfig) {
        this.notificationConfig = notificationConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationSendTime)) return false;
        if (!super.equals(o)) return false;

        NotificationSendTime that = (NotificationSendTime) o;

        return getHour().equals(that.getHour());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getHour().hashCode();
        return result;
    }
}
