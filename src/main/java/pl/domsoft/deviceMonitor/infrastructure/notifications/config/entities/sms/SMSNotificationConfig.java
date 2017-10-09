package pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SMSNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.NotificationConfig;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.SMSTargetModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szymo on 22.08.2017.
 * Encja bazowa konfiguracji powiadomień
 */
@Entity
@DiscriminatorValue("sms_config")
public class SMSNotificationConfig extends NotificationConfig<SMSNotificationsConfigCommand, SMSTarget> {

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "smsNotificationConfig")
    List<SMSTarget> smsTargets = new ArrayList<>();

    @Override
    public List<SMSTarget> getTargets() {
        return getSmsTargets();
    }

    public SMSNotificationConfig() {
    }

    public SMSNotificationConfig(SMSNotificationsConfigCommand smsNotificationsConfigCommand) {
        super(smsNotificationsConfigCommand.getSendNotificationsHours());
        if(smsTargets == null) this.smsTargets = new ArrayList<>();
        for (SMSTargetModel smsTargetModel : smsNotificationsConfigCommand.getTargets()) {
            this.smsTargets.add(new SMSTarget(smsTargetModel, this));
        }
    }

    public List<SMSTarget> getSmsTargets() {
        return smsTargets;
    }

    public void setSmsTargets(List<SMSTarget> smsTargets) {
        this.smsTargets = smsTargets;
    }

    @Override
    public SMSNotificationsConfigCommand toModel() {
        return new SMSNotificationsConfigCommand(this);
    }
}
