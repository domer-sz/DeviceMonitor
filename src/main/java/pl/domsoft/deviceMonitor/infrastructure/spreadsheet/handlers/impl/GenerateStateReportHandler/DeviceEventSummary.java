package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.impl.GenerateStateReportHandler;

import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

/**
 * Created by szymo on 04.06.2017.
 */
class DeviceEventSummary{
    private final Long breaksNumber;
    private final Long breaksTimeInSec;

    private final Long accidentsNumber;
    private final Long accidentsTimeInSec;

    private final Long overviewNumber;
    private final Long overviewTimeInSec;

    private final Integer timeOnCount;

    DeviceEventSummary(Long breaksNumber, Long breaksTimeInSec, Long accidentsNumber, Long accidentsTimeInSec, Long overviewNumber, Long overviewTimeInSec, Integer timeOnCount) {
        this.breaksNumber = breaksNumber;
        this.breaksTimeInSec = breaksTimeInSec;
        this.accidentsNumber = accidentsNumber;
        this.accidentsTimeInSec = accidentsTimeInSec;
        this.overviewNumber = overviewNumber;
        this.overviewTimeInSec = overviewTimeInSec;
        this.timeOnCount = timeOnCount;
    }

    public Long getBreaksNumber() {
        return breaksNumber;
    }

    public Long getBreaksTimeInSec() {
        return breaksTimeInSec;
    }

    public String getBreaksTimeFormated() {
        return formatSec(breaksTimeInSec);
    }

    public String getAccidentsTimeFormated() {
        return formatSec(accidentsTimeInSec);
    }

    public String getOverviewsTimeFormated() {
        return formatSec(overviewTimeInSec);
    }

    private String formatSec(Long seconds){
        return DateTimeUtils.formatSecToDDHHMMSS(seconds);
    }

    public Long getAccidentsNumber() {
        return accidentsNumber;
    }

    public Long getAccidentsTimeInSec() {
        return accidentsTimeInSec;
    }

    public Long getOverviewNumber() {
        return overviewNumber;
    }

    public Long getOverviewTimeInSec() {
        return overviewTimeInSec;
    }

    public int getTimeOnCount() {
        return timeOnCount;
    }
}
