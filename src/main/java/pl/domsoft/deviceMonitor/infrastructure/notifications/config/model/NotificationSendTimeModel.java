package pl.domsoft.deviceMonitor.infrastructure.notifications.config.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.NotificationSendTime;

/**
 * Created by szymo on 21.08.2017.
 * Obiekt przechowujący informację w jakich godzinach wysyłąć powiadomienia
 */
public class NotificationSendTimeModel {
    private final String hour;


    public NotificationSendTimeModel() {
        this.hour = "00:00";
    }

    @JsonCreator
    public NotificationSendTimeModel(
           @JsonProperty("hour") String hour
    ) {
        this.hour = hour;
    }

    public NotificationSendTimeModel(NotificationSendTime notificationSendTime) {
        this.hour = notificationSendTime.getHour();
    }

    public String getHour() {
        return hour;
    }
}
