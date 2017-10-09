package pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Localisation;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;
import pl.domsoft.deviceMonitor.infrastructure.device.model.EventsGather;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.email.commands.EmailCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendEmailNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.handlers.interfaces.SendEmailNotificationHandler;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by szymo on 27.08.2017.
 */
public class SendEmailNotificationHandlerImplTest extends IntegrationBaseTests{

    @Autowired
    private DeviceConfigRepository deviceConfigRepository;
    @Autowired
    private CommandBus commandBus;
    @Autowired
    private JavaMailSender javaMailSender;



    @Before
    public void setUp(){
        DeviceConfig config = new DeviceConfig
                (
                        "1567",
                        "dd",
                        new Localisation(
                                "5656",
                                "5654",
                                "Torun",
                                "87-100",
                                "Kniaziewicza",
                                "47",
                                "6"
                        ),
                        null
                );
        deviceConfigRepository.deleteAll();
        deviceConfigRepository.save(config);
    }

    @Test
    public void test_if_send_email_command_will_send_email() throws Exception {
        //given
        final MimeMessage mimeMessage = getMimeMessage();
        //when
        final Boolean sent = commandBus.sendCommand(new EmailCommand(mimeMessage), Boolean.class);
        //then
        assertEquals(true, sent);

    }

    private MimeMessage getMimeMessage() {
        EmailTarget emailTarget = new EmailTarget(true, true, true, "viwern@gmail.com", null);

        DeviceBreak deviceBreak = new DeviceBreak("1567", new Date(), new Date(), "dd");
        DeviceAccident deviceAccident = new DeviceAccident("1567", new Date(), new Date(), "dd");
        DeviceOverview deviceOverview = new DeviceOverview("1567", new Date(), new Date(), "dd");
        EventsGather gather = new EventsGather(Arrays.asList(deviceBreak, deviceBreak, deviceBreak, deviceBreak), Arrays.asList(deviceAccident), Arrays.asList(deviceOverview));

        SendEmailNotificationCommand sendEmailNotificationCommand = new SendEmailNotificationCommand(emailTarget, gather);
        return sendEmailNotificationCommand.updateEmail(javaMailSender.createMimeMessage(), deviceConfigRepository);
    }

}