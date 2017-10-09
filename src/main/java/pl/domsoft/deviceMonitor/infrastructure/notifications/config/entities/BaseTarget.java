package pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;
import pl.domsoft.deviceMonitor.infrastructure.device.model.EventsGather;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums.NotificationTypeEnum;

import javax.persistence.*;

/**
 * Created by szymo on 22.08.2017.
 */
@Entity
@Table(name = "notification_targets")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "target_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseTarget extends BaseEntity{

    @Column(name = "send_on_accident")
    protected Boolean sendOnAccident = false;
    @Column(name = "send_on_overview")
    protected Boolean sendOnOverview = false;
    @Column(name = "send_on_break")
    protected Boolean sendOnBreak = false;
    @Column(name = "target")
    protected String target = "";

    public BaseTarget() {
    }

    public BaseTarget(Boolean sendOnAccident, Boolean sendOnOverview, Boolean sendOnBreak, String target) {
        this.sendOnAccident = sendOnAccident == null ? false : sendOnAccident;
        this.sendOnOverview = sendOnOverview == null ? false : sendOnOverview;
        this.sendOnBreak = sendOnBreak == null ? false : sendOnBreak;
        this.target = target;
    }

    public abstract  NotificationTypeEnum getType();
    public abstract SendNotificationCommand toCommand(EventsGather gather);

    public boolean sendOnAccident(){
        if(this.sendOnAccident == null) return false;
        return this.sendOnAccident;
    }
    public boolean sendOnOverview(){
        if(this.sendOnOverview == null) return false;
        return this.sendOnOverview;
    }
    public boolean sendOnBreak(){
        if(this.sendOnBreak == null) return false;
        return this.sendOnBreak;
    }
    public String getTarget(){
        return this.target;
    }


}
