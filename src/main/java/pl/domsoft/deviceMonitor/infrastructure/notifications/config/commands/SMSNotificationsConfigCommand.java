package pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms.SMSNotificationConfig;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms.SMSTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces.SMSNotificationConfigHandler;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationSendTimeModel;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.SMSTargetModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Model widoku konfiguracji powiadomien sms
 * przechowuje informacje kiedy na jakie numery/emaile i jakie powiadomienia mając być
 * wysyłane
 * Created by szymo on 21.08.2017.
 */
public class SMSNotificationsConfigCommand extends BaseNotificationsConfigCommand<SMSTargetModel, SMSNotificationConfig> {

    @JsonCreator
    public SMSNotificationsConfigCommand(
            @JsonProperty("sendNotificationsHours") List<NotificationSendTimeModel> sendNotificationsHours,
            @JsonProperty("targets")  List<SMSTargetModel> targets
    ){
        super(sendNotificationsHours, targets);
    }

    public SMSNotificationsConfigCommand(SMSNotificationConfig smsNotificationConfig) {
        super(convertHourTime(smsNotificationConfig.getNotificationSendTimes()), convertTargets(smsNotificationConfig.getSmsTargets()));
    }

    private static List<SMSTargetModel> convertTargets(List<SMSTarget> smsTargets) {
        List<SMSTargetModel> targetModels = new ArrayList<>(smsTargets.size());
        for (SMSTarget target: smsTargets) {
            targetModels.add(new SMSTargetModel(target));
        }

        return targetModels;
    }
    @JsonIgnore
    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return SMSNotificationConfigHandler.class;
    }

    @JsonIgnore
    @Override
    public boolean isLoggable() {
        return true;
    }

    @Override
    public SMSNotificationConfig toEntity() {
        return new SMSNotificationConfig(this);
    }
}
