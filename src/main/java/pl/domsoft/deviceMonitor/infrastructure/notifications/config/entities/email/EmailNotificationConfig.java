package pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.EmailNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.NotificationConfig;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.EmailTargetModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szymo on 22.08.2017.
 * Encja bazowa konfiguracji powiadomień
 */
@Entity
@DiscriminatorValue("email_config")
public class EmailNotificationConfig extends NotificationConfig<EmailNotificationsConfigCommand, EmailTarget>{

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "emailNotificationConfig")
    List<EmailTarget> emailTargets = new ArrayList<>();

    @Override
    public List<EmailTarget> getTargets() {
        return getEmailTargets();
    }

    public EmailNotificationConfig() {
    }

    public EmailNotificationConfig(EmailNotificationsConfigCommand emailNotificationsConfigCommand) {
        super(emailNotificationsConfigCommand.getSendNotificationsHours());
        if(emailTargets == null) emailTargets = new ArrayList<>();
        for (EmailTargetModel emailTargetModel : emailNotificationsConfigCommand.getTargets()) {
            emailTargets.add(new EmailTarget(emailTargetModel, this));
        }
    }

    public List<EmailTarget> getEmailTargets() {
        return emailTargets;
    }

    public void setEmailTargets(List<EmailTarget> emailTargets) {
        this.emailTargets = emailTargets;
    }

    @Override
    public EmailNotificationsConfigCommand toModel() {
        return new EmailNotificationsConfigCommand(this);
    }
}
