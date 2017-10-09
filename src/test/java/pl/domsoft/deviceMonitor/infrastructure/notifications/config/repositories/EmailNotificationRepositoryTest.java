package pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailNotificationConfig;

import static org.junit.Assert.*;

/**
 * Created by szymo on 22.08.2017.
 */
public class EmailNotificationRepositoryTest extends IntegrationBaseTests{

    @Autowired
    EmailNotificationRepository emailNotificationRepository;

    @Before
    public void setUp(){
        emailNotificationRepository.deleteAll();
    }

    @Test
    public void test_if_get_config_from_repository_when_there_are_no_config_will_produce_empty_config() {
        //given
        //when
        final EmailNotificationConfig config = emailNotificationRepository.findConfig();
        //then
        assertNotNull(config);
        assertEquals(1, emailNotificationRepository.findAll().size());

    }
}