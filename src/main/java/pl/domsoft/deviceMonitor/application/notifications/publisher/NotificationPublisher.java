package pl.domsoft.deviceMonitor.application.notifications.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;
import pl.domsoft.deviceMonitor.infrastructure.device.model.EventsGather;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceAccidentRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceBreakRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceOverviewRepository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.BaseTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.NotificationConfig;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.NotificationSendTime;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories.EmailNotificationRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by szymo on 24.08.2017.
 * Timer wykonujący się co godzinę i wysyłający powiadomiena według konfiguracji  z {@link pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.NotificationConfig}
 */
@Component
public class NotificationPublisher {

    private static final Logger log = LoggerFactory.getLogger(NotificationPublisher.class);


    private final CommandBus commandBus;
    private final EmailNotificationRepository emailNotificationRepository;

    private final DeviceBreakRepository deviceBreakRepository;
    private final DeviceAccidentRepository deviceAccidentRepository;
    private final DeviceOverviewRepository deviceOverviewRepository;

    public NotificationPublisher(CommandBus commandBus, EmailNotificationRepository emailNotificationRepository, DeviceBreakRepository deviceBreakRepository, DeviceAccidentRepository deviceAccidentRepository, DeviceOverviewRepository deviceOverviewRepository) {
        this.commandBus = commandBus;
        this.emailNotificationRepository = emailNotificationRepository;
        this.deviceBreakRepository = deviceBreakRepository;
        this.deviceAccidentRepository = deviceAccidentRepository;
        this.deviceOverviewRepository = deviceOverviewRepository;
    }

    /**
     * Sprawdzenie czy trzeba wysłać jakieś powiadomienia wykonuje się co godzinę
     */
    @Scheduled(cron = "0 0/60 * * * ?")
    public void checkAndPublishNotifications(){
        NotificationConfig config = loadConfig();
        EventsGather eventsGather = buildEventGather();
        LocalTime now = DateTimeUtils.getCurrentLocalTime();
        this.executeCheckAndSendNotification(config, eventsGather, now);
        //jeśli był błąd przy wysyłaniu powiadomień to obecnie wydarzenie i tak zostanie oznaczone jako powiadomione
        setPublishedStatus(eventsGather);
    }

    private void setPublishedStatus(EventsGather eventsGather) {
        final List<DeviceAccident> deviceAccidents = eventsGather.getDeviceAccidents();
        for (DeviceAccident deviceAccident : deviceAccidents) {
            deviceAccident.setNotificationSent(true);
            deviceAccidentRepository.save(deviceAccident);
        }

        final List<DeviceBreak> deviceBreaks = eventsGather.getDeviceBreaks();
        for (DeviceBreak deviceBreak : deviceBreaks) {
            deviceBreak.setNotificationSent(true);
            deviceBreakRepository.save(deviceBreak);
        }

        final List<DeviceOverview> deviceOverviews = eventsGather.getDeviceOverviews();
        for (DeviceOverview deviceOverview : deviceOverviews) {
            deviceOverview.setNotificationSent(true);
            deviceOverviewRepository.save(deviceOverview);
        }
    }

    private EventsGather buildEventGather() {
        return new EventsGather(
                deviceBreakRepository.findEventsWithoutNotifications(),
                deviceAccidentRepository.findEventsWithoutNotifications(),
                deviceOverviewRepository.findEventsWithoutNotifications()
        );
    }

    void executeCheckAndSendNotification(NotificationConfig config, EventsGather eventsGather, LocalTime now) {
        final List<NotificationSendTime> notificationSendTimes = config.getNotificationSendTimes();
        final Optional<NotificationSendTime> currentMatchedSendTime = notificationSendTimes.stream().filter(time -> time.getHour().equals(String.valueOf(now.getHour())))
                .findAny();
        if(currentMatchedSendTime.isPresent()){
            for (BaseTarget baseTarget: (List<BaseTarget>)config.getTargets()) {
                final SendNotificationCommand sendNotificationCommand = baseTarget.toCommand(eventsGather);
                try {
                    final Boolean sent = commandBus.sendCommand(sendNotificationCommand, Boolean.class);
                    if(sent){
                        log.info("Poprawnie wysłano powiadomienie do: " +baseTarget.getTarget());
                    }else{
                        log.info("Nie wysłano powiadomienie do: " +baseTarget.getTarget());
                    }
                } catch (AppException e) {
                    e.printStackTrace();
                    log.error("Wystąpił wyjątek podczas wysyłania powiadomienia do: " +baseTarget.getTarget() +" \n " + e.getStackInfo());
                }
            }
        }

    }

    private NotificationConfig loadConfig() {
        return emailNotificationRepository.findConfig();
    }
}


