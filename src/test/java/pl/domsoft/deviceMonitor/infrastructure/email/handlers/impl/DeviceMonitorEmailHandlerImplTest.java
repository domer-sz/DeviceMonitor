package pl.domsoft.deviceMonitor.infrastructure.email.handlers.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.email.commands.EmailCommand;
import pl.domsoft.deviceMonitor.infrastructure.email.handlers.interfaces.EmailHandler;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Date;

import static org.junit.Assert.assertTrue;


/**
 * Created by szymo on 27.08.2017.
 */
public class DeviceMonitorEmailHandlerImplTest extends IntegrationBaseTests{

    @Autowired
    private EmailHandler emailHandler;

    @Autowired
    public CommandBus commandBus;

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void send_email_on_command() throws AppException {
        //given
        final Boolean sent = commandBus.sendCommand(createEmail(), Boolean.class);
        assertTrue(sent);

    }

    private EmailCommand createEmail() {
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mail, true);
            helper.setTo("szymondomeracki@outlook.com");
            helper.setSubject("TestEmailZDeviceMonitor");
            helper.setText("Test<br>Test", true);
            helper.setSentDate(new Date());
            return new EmailCommand(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }

    }

}