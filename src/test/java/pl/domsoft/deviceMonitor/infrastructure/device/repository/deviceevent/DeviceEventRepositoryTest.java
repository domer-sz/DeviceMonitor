package pl.domsoft.deviceMonitor.infrastructure.device.repository.deviceevent;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceEventRepository;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by szymo on 29.05.2017.
 */
public class DeviceEventRepositoryTest extends IntegrationBaseTests {

    @Autowired
    private DeviceEventRepository deviceEventRepository;

    @Before
    public void setUp(){
        deviceEventRepository.deleteAll();
    }

    @Test
    public void testIfSaveInH2DatabaseWork() {
        //given
        final DeviceBreak deviceBreak = new DeviceBreak("ranodm", new Date(), new Date(), "commnet");
        //when
        deviceEventRepository.save(deviceBreak);

        //then
        assertEquals(1, deviceEventRepository.findAll().size());
    }
}
