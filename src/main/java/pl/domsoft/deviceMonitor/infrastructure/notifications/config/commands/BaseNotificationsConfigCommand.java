package pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands;

import com.fasterxml.jackson.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.NotificationSendTime;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.EmailTargetModel;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationSendTimeModel;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationTargetModel;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.SMSTargetModel;
import pl.domsoft.deviceMonitor.infrastructure.shared.model.converts.interfaces.Persistable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Bazowy Model widoku konfiguracji powiadomien email/sms
 * przechowuje informacje kiedy na jakie numery/emaile i jakie powiadomienia mając być
 * wysyłane
 * Created by szymo on 21.08.2017.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailNotificationsConfigCommand.class, name = "EMAIL"),
        @JsonSubTypes.Type(value = SMSNotificationsConfigCommand.class, name = "SMS")
})
public abstract class BaseNotificationsConfigCommand<Ttarget extends NotificationTargetModel, TEntity extends BaseEntity>
        implements Serializable, Command, Persistable<TEntity>{

    private final List<NotificationSendTimeModel> sendNotificationsHours;
    private final List<Ttarget> targets;

    @JsonCreator
    public BaseNotificationsConfigCommand(
           @JsonProperty("sendNotificationsHours") List<NotificationSendTimeModel> sendNotificationsHours,
           @JsonProperty("targets")  List<Ttarget> targets
    ) {
        this.sendNotificationsHours = sendNotificationsHours;
        this.targets = targets;
    }

    public List<NotificationSendTimeModel> getSendNotificationsHours() {
        return sendNotificationsHours;
    }

    public List<Ttarget> getTargets() {
        return targets;
    }

    protected static List<NotificationSendTimeModel> convertHourTime(List<NotificationSendTime> notificationSendTimes) {
        List<NotificationSendTimeModel> result = new ArrayList<>(notificationSendTimes.size());
        for (NotificationSendTime hour: notificationSendTimes) {
            result.add(new NotificationSendTimeModel(hour));
        }

        return result;
    }

    @JsonIgnore
    public static EmailNotificationsConfigCommand getEmptyEmailConfigDto(){
        return new EmailNotificationsConfigCommand(Collections.singletonList(new NotificationSendTimeModel()), Collections.singletonList(new EmailTargetModel()));
    }

    @JsonIgnore
    public static SMSNotificationsConfigCommand getEmptySMSConfigDto(){
        return new SMSNotificationsConfigCommand(Collections.singletonList(new NotificationSendTimeModel()), Collections.singletonList(new SMSTargetModel()));
    }
}
