package pl.domsoft.deviceMonitor.infrastructure.notifications.config.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums.NotificationTypeEnum;

/**
 * Created by szymo on 21.08.2017.
 * Cel powiadomienia
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailTargetModel.class, name = "EMAIL"),
        @JsonSubTypes.Type(value = SMSTargetModel.class, name = "SMS")
})
public interface NotificationTargetModel {
    NotificationTypeEnum getType();
    boolean sendOnAccident();
    boolean sendOnOverview();
    boolean sendOnBreak();
    String getTarget();
}
