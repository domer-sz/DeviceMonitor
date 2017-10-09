package pl.domsoft.deviceMonitor.deviceloganalyzer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.application.deviceloganalyzer.DeviceLogAnalyzer;
import pl.domsoft.deviceMonitor.infrastructure.appstateholder.AppStateHolder;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceLog;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceBreakRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog.DeviceLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by szymo on 30.05.2017.
 */
public class DeviceLogAnalyzerTest extends IntegrationBaseTests {

    private static final Logger log = LoggerFactory.getLogger(AppException.class);


    @Autowired
    private DeviceLogRepository deviceLogRepository;

    @Autowired
    private DeviceConfigRepository deviceConfigRepository;

    @Autowired
    private DeviceBreakRepository deviceBreakRepository;

    @Autowired
    private DeviceLogAnalyzer deviceLogAnalyzer;

    @Autowired
    private AppStateHolder stateHolder;

    @Value("${device.log.analyzer.rate.in.milliseconds}")
    private int cycleTimeInMs;

    @Value("${number.of.cycles.to.alert}")
    private int numberOfCyclesToAlert;

    @Before
    public void setUp(){
        deviceLogRepository.deleteAll();
        deviceConfigRepository.deleteAll();
        deviceBreakRepository.deleteAll();
    }

//    @Test
//    public void testIfCreateDeviceOffStateWhereThereIsNoLog(){
//        //given
//        prepareConfigs(3);
//
//        //when
//        deviceLogAnalyzer.scheduleLogAnalyze();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //then
//        assertEquals(3, deviceConfigRepository.findAll().size());
//        assertEquals(3, deviceBreakRepository.findAll().size());
//
//    }

//    @Test
//    @Ignore
//    public void testIfCreateDeviceOffStateWhereThereIsLogButOld(){
//        //given
//        prepareConfigs(3);
//        final Collection<DeviceConfig> allConfigs = deviceConfigRepository.findAll();
//        allConfigs.stream().forEach(conf -> {
//            DeviceLog deviceLog = DeviceLog
//                    .buildDeviceLogWithRandomData(conf.getDeviceId(), DateTimeUtils
//                            .localDateTimeToDate( DateTimeUtils.getCurrentLocalDateTime().minusMinutes(cycleTimeInMs*(numberOfCyclesToAlert+1))));
//            deviceLogRepository.save(deviceLog);
//
//        });
//
//        //when
//        deviceLogAnalyzer.scheduleLogAnalyze();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //then
//        assertEquals(3, deviceBreakRepository.findAll().size());
//
//    }

    @Test
    public void testIfCreateDeviceOnStateWhereThereIsFreshLog(){
        //given
        prepareConfigs(3);
        final Collection<DeviceConfig> allConfigs = deviceConfigRepository.findAll();
        allConfigs.stream().forEach(conf -> {
            DeviceLog deviceLog = DeviceLog
                    .buildDeviceLogWithRandomData(conf.getDeviceId(), DateTimeUtils
                            .localDateTimeToDate( DateTimeUtils.getCurrentLocalDateTime().minusNanos(10*cycleTimeInMs*(numberOfCyclesToAlert-1))));
            deviceLogRepository.save(deviceLog);

        });

        //when
        deviceLogAnalyzer.scheduleLogAnalyze();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //then
        assertEquals(0, deviceBreakRepository.findAll().size());
        stateHolder.getCurrentState().stream().forEach(state ->{
            assertEquals(true, state.isOn());
        });

    }

    @Transactional
    private void prepareConfigs(int n) {
        for (int i = 0; i < n ; i++){
            deviceConfigRepository.save(new DeviceConfig(UUID.randomUUID().toString(), "", null, null));
        }
    }
}
