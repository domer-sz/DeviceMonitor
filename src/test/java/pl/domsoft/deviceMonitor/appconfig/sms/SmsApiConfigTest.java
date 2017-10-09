package pl.domsoft.deviceMonitor.appconfig.sms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.domsoft.IntegrationBaseTests;
import pl.smsapi.Client;
import pl.smsapi.api.action.sms.SMSSend;

import static org.junit.Assert.*;

/**
 * Created by szymo on 11.09.2017.
 */
public class SmsApiConfigTest extends IntegrationBaseTests{

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test_if_container_will_return_only_singleton() throws Exception {
        Class<? extends Client> clientClass = Client.class;
        Client client1 = applicationContext.getBean(clientClass);
        Client client2  = applicationContext.getBean(clientClass);

        assertEquals(client1, client2);
    }

    @Test
    public void test_if_container_return_correct_sms_action() throws Exception {
        Class<? extends SMSSend> clientClass = SMSSend.class;
        SMSSend sms1 = applicationContext.getBean(clientClass);
        SMSSend sms2  = applicationContext.getBean(clientClass);
        assertNotEquals(sms1, sms2);
    }

}