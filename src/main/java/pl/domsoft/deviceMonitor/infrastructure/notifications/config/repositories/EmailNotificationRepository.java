package pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailNotificationConfig;

import java.util.Collection;
import java.util.List;

/**
 * Created by szymo on 22.08.2017.
 */
@Repository
public interface EmailNotificationRepository extends CrudRepository<EmailNotificationConfig, Long>, EmailNotificationCustomRepository {
    List<EmailNotificationConfig> findAll();
}
