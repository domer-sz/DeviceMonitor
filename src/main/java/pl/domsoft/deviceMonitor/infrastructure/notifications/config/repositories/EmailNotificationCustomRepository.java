package pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories;

import org.springframework.transaction.annotation.Transactional;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailNotificationConfig;

/**
 * Created by szymo on 22.08.2017.
 */
interface EmailNotificationCustomRepository {
    @Transactional
    EmailNotificationConfig findConfig();
}
