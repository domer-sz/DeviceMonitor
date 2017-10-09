package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.impl.GenerateStateReportHandler;

import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by szymo on 04.06.2017.
 */
class EventTimeModel{
    private final Date start;
    private final Date end;
    private final String deviceId;

    public EventTimeModel(Date start, Date end, String deviceId) {
        this.start = start;
        this.end = end;
        this.deviceId = deviceId;
    }

    public EventTimeModel(LocalDateTime start, LocalDateTime end, String deviceId) {
        this.start = DateTimeUtils.localDateTimeToDate(start);
        this.end = DateTimeUtils.localDateTimeToDate(end);
        this.deviceId = deviceId;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getTimeDiffrenceInSec(){
        return getTimeDiffrence(ChronoUnit.SECONDS);
    }

    private Long getTimeDiffrence(ChronoUnit unit) {
        LocalDateTime localStar  = DateTimeUtils.dateToLocalDateTime(start);
        LocalDateTime localEnd = DateTimeUtils.dateToLocalDateTime(end);
        return  localStar.until(localEnd, unit);
    }

    @Override
    public String toString() {
        return "EventTimeModel{" +
                "start=" + start +
                ", end=" + end +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
