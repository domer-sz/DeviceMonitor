package pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.EmailNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SMSNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories.EmailNotificationRepository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories.SMSNotificationRepository;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by szymo on 22.08.2017.
 */
public class SMSNotificationConfigHandlerImplTest extends IntegrationBaseTests{

    @Autowired
    private SMSNotificationRepository smsNotificationRepository;

    @Autowired
    CommandBus commandBus;

    @Before
    public void setUp(){
        smsNotificationRepository.deleteAll();
    }

    @Test
    public void test_if_after_send_two_commands_there_will_be_only_pne_config_in_database() throws Exception {
        //given
        //when
        sendTwoCreateConfigCommand();
        //then
        assertEquals(1, smsNotificationRepository.findAll().size());

    }

    private void sendTwoCreateConfigCommand() throws pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException {
        commandBus.sendCommand(new SMSNotificationsConfigCommand(new ArrayList<>(), new ArrayList<>()));
        commandBus.sendCommand(new SMSNotificationsConfigCommand(new ArrayList<>(), new ArrayList<>()));
    }
}