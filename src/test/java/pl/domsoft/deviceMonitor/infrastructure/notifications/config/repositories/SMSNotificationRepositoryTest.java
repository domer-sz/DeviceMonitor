package pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.EmailNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailNotificationConfig;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms.SMSNotificationConfig;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by szymo on 22.08.2017.
 */
public class SMSNotificationRepositoryTest extends IntegrationBaseTests{

    @Autowired
    private SMSNotificationRepository smsNotificationRepository;

    @Before
    public void setUp(){
        smsNotificationRepository.deleteAll();
    }

    @Test
    public void test_if_get_config_from_repository_when_there_are_no_config_will_produce_empty_config() {
        //given
        //when
        final SMSNotificationConfig config = smsNotificationRepository.findConfig();
        //then
        assertNotNull(config);
        assertEquals(1, smsNotificationRepository.findAll().size());

    }

}