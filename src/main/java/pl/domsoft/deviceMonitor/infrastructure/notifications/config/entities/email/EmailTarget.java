package pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email;

import pl.domsoft.deviceMonitor.infrastructure.device.model.EventsGather;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendEmailNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.BaseTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums.NotificationTypeEnum;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.EmailTargetModel;

import javax.persistence.*;

/**
 * Created by szymo on 22.08.2017.
 */
@Entity
@DiscriminatorValue("email_target")
public class EmailTarget extends BaseTarget{

    @ManyToOne
    @JoinColumn(name = "config_id")
    EmailNotificationConfig emailNotificationConfig;

    public EmailTarget() {
    }

    public EmailTarget(boolean sendOnAccident, boolean sendOnOverview, boolean sendOnBreak, String target, EmailNotificationConfig emailNotificationConfig) {
        super(sendOnAccident, sendOnOverview, sendOnBreak, target);
        this.emailNotificationConfig = emailNotificationConfig;
    }

    public EmailTarget(EmailTargetModel emailTargetModel, EmailNotificationConfig emailNotificationConfig) {
        super();
        this.target = emailTargetModel.getTarget();
        this.sendOnAccident = emailTargetModel.sendOnAccident();
        this.sendOnBreak = emailTargetModel.sendOnBreak();
        this.sendOnOverview = emailTargetModel.sendOnOverview();
        this.emailNotificationConfig = emailNotificationConfig;
    }

    @Override
    public NotificationTypeEnum getType() {
        return NotificationTypeEnum.EMAIL;
    }

    @Override
    public SendNotificationCommand toCommand(EventsGather gather) {
        return new SendEmailNotificationCommand(this, gather);
    }
}
