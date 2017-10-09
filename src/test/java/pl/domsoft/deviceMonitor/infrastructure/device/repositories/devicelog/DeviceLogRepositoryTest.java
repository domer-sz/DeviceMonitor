package pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceLog;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.util.Date;

/**
 * Created by szymo on 04.09.2017.
 */
public class DeviceLogRepositoryTest extends IntegrationBaseTests{

    @Autowired
    private DeviceLogRepository deviceLogRepository;


    @Value("${months.after.logs.will.be.deleted}")
    private long monthsAfterLogsWillBeDeleted;


    @Before
    public void setUp(){
        deviceLogRepository.deleteAll();
    }

    @Test
    public void deleteOldLogs() throws Exception {
        //given
        create50OldLogsAnd50NewLogs(monthsAfterLogsWillBeDeleted);
        //when
        deleteOnlyOldLogs();
        //then
        onlyNewLogsLeft();
    }

    private void onlyNewLogsLeft() {
        Assert.assertEquals(50, deviceLogRepository.findAll().size());
    }

    private void deleteOnlyOldLogs() {
        final Date limitDate = DateTimeUtils.localDateTimeToDate(DateTimeUtils.getCurrentLocalDateTime().minusMonths(monthsAfterLogsWillBeDeleted ));
        deviceLogRepository.deleteOldLogs(limitDate);

    }

    private void create50OldLogsAnd50NewLogs(long monthsAfterLogsWillBeDeleted) {
        for (int i = 0; i < 50; i++) {
            DeviceLog deviceLog = DeviceLog.buildDeviceLogWithRandomData(
                    "1",
                    DateTimeUtils.localDateTimeToDate(
                            DateTimeUtils
                                    .getCurrentLocalDateTime()
                                    .minusMonths(monthsAfterLogsWillBeDeleted+1)
                    )
            );
            deviceLogRepository.save(deviceLog);
        }

        for (int i = 0; i < 50; i++) {
            DeviceLog deviceLog = DeviceLog.buildDeviceLogWithRandomData(
                    "1",
                    DateTimeUtils
                            .localDateTimeToDate(
                                    DateTimeUtils
                                            .getCurrentLocalDateTime()
                                            .minusMonths(monthsAfterLogsWillBeDeleted-1)
                            )
            );
            deviceLogRepository.save(deviceLog);
        }
    }

}