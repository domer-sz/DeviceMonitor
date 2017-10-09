package pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailNotificationConfig;

import java.util.List;

/**
 * Created by szymo on 22.08.2017.
 */
class EmailNotificationRepositoryImpl implements EmailNotificationCustomRepository {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Override
    public EmailNotificationConfig findConfig() {
        final List<EmailNotificationConfig> all = emailNotificationRepository.findAll();
        if(all.isEmpty()){
            all.add(new EmailNotificationConfig());
            emailNotificationRepository.save(all);
        }
        return all.get(0);
    }
}
