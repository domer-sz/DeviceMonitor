package pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands;

import org.springframework.mail.javamail.MimeMessageHelper;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;
import pl.domsoft.deviceMonitor.infrastructure.device.model.EventsGather;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.BaseTarget;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

/**
 * Created by szymo on 26.08.2017.
 * Bazowa klasa komend wysyłającyhc zdarzenia
 */
public abstract class SendNotificationCommand<TTarget extends BaseTarget> implements Command {

    protected TTarget target;
    protected EventsGather eventsGather;

    public SendNotificationCommand(TTarget target, EventsGather eventsGather) {
        this.target = target;
        this.eventsGather = eventsGather;
    }

    public TTarget getTarget() {
        return target;
    }

    public void setTarget(TTarget target) {
        this.target = target;
    }

    public EventsGather getEventsGather() {
        return eventsGather;
    }

    public void setEventsGather(EventsGather eventsGather) {
        this.eventsGather = eventsGather;
    }



}
