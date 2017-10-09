package pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailNotificationConfig;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms.SMSNotificationConfig;

import java.util.List;

/**
 * Created by szymo on 22.08.2017.
 */
class SMSNotificationRepositoryImpl implements SMSNotificationCustomRepostiory {
    @Autowired
    private SMSNotificationRepository smsNotificationRepository;

    @Override
    public SMSNotificationConfig findConfig() {
        final List<SMSNotificationConfig> all = smsNotificationRepository.findAll();
        if(all.isEmpty()){
            all.add(new SMSNotificationConfig());
            smsNotificationRepository.save(all);
        }
        return all.get(0);
    }
}
