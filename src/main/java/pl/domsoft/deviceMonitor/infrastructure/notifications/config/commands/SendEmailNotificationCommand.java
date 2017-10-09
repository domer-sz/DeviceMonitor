package pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands;

import org.springframework.mail.javamail.MimeMessageHelper;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;
import pl.domsoft.deviceMonitor.infrastructure.device.model.EventsGather;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces.SendEmailNotificationHandler;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

/**
 * Created by szymo on 26.08.2017.
 */
public class SendEmailNotificationCommand extends SendNotificationCommand<EmailTarget> {

    public SendEmailNotificationCommand(EmailTarget target, EventsGather eventsGather) {
        super(target, eventsGather);
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return SendEmailNotificationHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }



    public MimeMessage updateEmail(MimeMessage mail, DeviceConfigRepository deviceConfigRepository){

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mail, true);
            helper.setTo(this.target.getTarget());
            helper.setSubject("Powiadomienie z systemu monitorowania oczyszczalni ścieków");
            helper.setText(prepareMailMessageContentText(eventsGather, deviceConfigRepository), true);
            helper.setSentDate(new Date());
            return mail;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String prepareMailMessageContentText(EventsGather eventsGather, DeviceConfigRepository deviceConfigRepository) {
        String start = "Powiadomienie z systemu monitorowania oczyszczalni ścieków<br><br>Przerwy:<br>";
        StringBuilder contentBuilder = new StringBuilder(start);
        if(this.target.sendOnBreak()){
            final List<DeviceBreak> deviceBreaks = eventsGather.getDeviceBreaks();
            if(!deviceBreaks.isEmpty()) {
                for (DeviceBreak deviceBreak : deviceBreaks) {
                    final DeviceConfig config = deviceConfigRepository.findFirstDeviceConfigByDeviceId(deviceBreak.getDeviceId());
                    contentBuilder.append(deviceBreak.toTextLine(config));
                    contentBuilder.append("<br>");
                }
            }else{
                contentBuilder.append("Brak przerw");
            }
        }
        contentBuilder.append("<br>Awarie:<br>");
        if(this.target.sendOnAccident()){
            final List<DeviceAccident> deviceAccidents = eventsGather.getDeviceAccidents();

            if(!deviceAccidents.isEmpty()) {
                for (DeviceAccident deviceAccident : deviceAccidents) {
                    contentBuilder.append(deviceAccident.toTextLine(deviceConfigRepository.findFirstDeviceConfigByDeviceId(deviceAccident.getDeviceId())));
                    contentBuilder.append("<br>");
                }
            }else {
                contentBuilder.append("Brak awarii");
            }
        }
        contentBuilder.append("<br>Pzeglądy:<br>");
        if(this.target.sendOnOverview()){
            final List<DeviceOverview> deviceOverviews = eventsGather.getDeviceOverviews();
            if(!deviceOverviews.isEmpty()) {
                for (DeviceOverview deviceOverview : deviceOverviews) {
                    contentBuilder.append(deviceOverview.toTextLine(deviceConfigRepository.findFirstDeviceConfigByDeviceId(deviceOverview.getDeviceId())));
                    contentBuilder.append("<br>");
                }
            }else {
                contentBuilder.append("brak przeglądów");
            }
        }
        final String content = contentBuilder.toString();
        return content;
    }

}
