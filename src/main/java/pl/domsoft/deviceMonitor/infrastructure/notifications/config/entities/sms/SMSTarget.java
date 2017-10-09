package pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms;

import pl.domsoft.deviceMonitor.infrastructure.device.model.EventsGather;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendSMSNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.BaseTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums.NotificationTypeEnum;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.SMSTargetModel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by szymo on 22.08.2017.
 */
@Entity
@DiscriminatorValue("sms_target")
public class SMSTarget extends BaseTarget{

    @ManyToOne
    @JoinColumn(name = "config_id")
    SMSNotificationConfig smsNotificationConfig;

    public SMSTarget() {
    }

    public SMSTarget(String phoneNumber){
        this.target = phoneNumber;
    }

    public SMSTarget(SMSTargetModel smsTargetModel, pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms.SMSNotificationConfig smsNotificationConfig) {
        this.target = smsTargetModel.getTarget();
        this.sendOnAccident = smsTargetModel.sendOnAccident();
        this.sendOnBreak = smsTargetModel.sendOnBreak();
        this.sendOnOverview = smsTargetModel.sendOnOverview();
        this.smsNotificationConfig = smsNotificationConfig;
    }

    @Override
    public NotificationTypeEnum getType() {
        return NotificationTypeEnum.EMAIL;
    }

    @Override
    public SendNotificationCommand toCommand(EventsGather gather) {
        return new SendSMSNotificationCommand(this, gather);
    }
}
