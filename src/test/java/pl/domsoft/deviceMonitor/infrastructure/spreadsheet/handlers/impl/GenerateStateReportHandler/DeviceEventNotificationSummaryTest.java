package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.impl.GenerateStateReportHandler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by szymo on 05.06.2017.
 */
public class DeviceEventNotificationSummaryTest {
    @Test
    public void getBreaksNumber() throws Exception {
        //given
        long seconds = 3600*24*3L+3666;
        final DeviceEventSummary deviceEventSummary = new DeviceEventSummary(0l, seconds, 0l, 0l, 0l, 0l, 0);
        //when
        final String breaksTimeFormated = deviceEventSummary.getBreaksTimeFormated();
        //then
        assertEquals("3D 1H 1m 6s ", breaksTimeFormated);

    }

}