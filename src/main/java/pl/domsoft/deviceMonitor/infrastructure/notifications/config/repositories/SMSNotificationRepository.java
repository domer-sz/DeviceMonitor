package pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms.SMSNotificationConfig;

import java.util.Collection;
import java.util.List;

/**
 * Created by szymo on 22.08.2017.
 */
@Repository
public interface SMSNotificationRepository extends CrudRepository<SMSNotificationConfig, Long>, SMSNotificationCustomRepostiory{
    List<SMSNotificationConfig> findAll();
}
