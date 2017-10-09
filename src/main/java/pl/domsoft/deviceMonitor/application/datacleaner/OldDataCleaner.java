package pl.domsoft.deviceMonitor.application.datacleaner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog.DeviceLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

/**
 * Created by szymo on 04.09.2017.
 * Czyśći dane starsze niż x lat, x zdefiniowane w application.properies
 */
@Component
public class OldDataCleaner {

    private final DeviceLogRepository deviceLogRepository;


    @Value("${months.after.logs.will.be.deleted}")
    private long monthsAfterLogsWillBeDeleted;

    public OldDataCleaner(DeviceLogRepository deviceLogRepository) {
        this.deviceLogRepository = deviceLogRepository;
    }

    /**
     * Wykonuje się w każdy poniedziałek o 3:00
     */
    @Scheduled(cron = "0 0 3 ? * MON ")
    public void doClean(){
        try{
            deviceLogRepository.deleteOldLogs(
                    DateTimeUtils
                            .localDateTimeToDate(
                                    DateTimeUtils.getCurrentLocalDateTime().minusMonths(monthsAfterLogsWillBeDeleted)
                            )
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
