package pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums;

import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.EmailTargetModel;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationTargetModel;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.SMSTargetModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by szymo on 21.08.2017.
 * rzechowuje typy powiadomień dotępnych w aplikacji
 */
public enum NotificationTypeEnum {
    EMAIL(0, "EMAIL"),
    SMS(1, "SMS");

    private int id;
    private String label;

    NotificationTypeEnum(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    @Override
    public final String toString() {
        return label;
    }

    public static List<NotificationTargetModel> getTargetEmptyDtos(){
        return Arrays.asList(new SMSTargetModel(), new EmailTargetModel());
    }
}
