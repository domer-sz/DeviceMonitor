package pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.model.EventsGather;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms.SMSTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces.SendSMSNotificationHandler;

import java.util.List;

/**
 * Created by szymo on 26.08.2017.
 */
public class SendSMSNotificationCommand extends SendNotificationCommand<SMSTarget> {

    public SendSMSNotificationCommand(SMSTarget target, EventsGather eventsGather) {
        super(target, eventsGather);
    }

    public Object updateSms(DeviceConfigRepository deviceConfigRepository) {
        String start = "Powiadomienie z systemu monitorowania oczyszczalni ścieków<br><br>Przerwy:<br>";
        StringBuilder contentBuilder = new StringBuilder(start);
        if(!this.eventsGather.getDeviceBreaks().isEmpty()){
            final List<DeviceBreak> deviceBreaks = eventsGather.getDeviceBreaks();
            for ( DeviceBreak deviceBreak : deviceBreaks) {
                final DeviceConfig config = deviceConfigRepository.findFirstDeviceConfigByDeviceId(deviceBreak.getDeviceId());
                contentBuilder.append(deviceBreak.toTextLine(config));
                contentBuilder.append("<br>");
            }
        }
        contentBuilder.append("<br>Awarie:<br>");
        if(!this.eventsGather.getDeviceAccidents().isEmpty()){
            final List<DeviceAccident> deviceAccidents = eventsGather.getDeviceAccidents();
            for ( DeviceAccident deviceAccident : deviceAccidents) {
                contentBuilder.append(deviceAccident.toTextLine(deviceConfigRepository.findFirstDeviceConfigByDeviceId(deviceAccident.getDeviceId())));
                contentBuilder.append("<br>");
            }
        }
        return contentBuilder.toString();
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return SendSMSNotificationHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
