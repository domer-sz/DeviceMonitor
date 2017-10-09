package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.impl.GenerateStateReportHandler;

import org.junit.Test;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Created by szymo on 05.06.2017.
 */
public class EventTimeModelTest {
    @Test
    public void TetIfgetTimeDiffrenceInSecWorkWithHours() throws Exception {
        //given
        EventTimeModel eventTimeModel = new EventTimeModel(
                DateTimeUtils.localDateTimeToDate(LocalDateTime.of(2000,4,4,12,0,0)),
                DateTimeUtils.localDateTimeToDate(LocalDateTime.of(2000,4,4,18,0,0)),
                ""
        );
        //when
        final Long timeDiffrenceInSec = eventTimeModel.getTimeDiffrenceInSec();
        //then
        final Long expected = 6*3600L;
        assertEquals(expected, timeDiffrenceInSec);
    }

    @Test
    public void TetIfgetTimeDiffrenceInSecWorkWithDays() throws Exception {
        //given
        EventTimeModel eventTimeModel = new EventTimeModel(
                DateTimeUtils.localDateTimeToDate(LocalDateTime.of(2000,4,4,12,0,0)),
                DateTimeUtils.localDateTimeToDate(LocalDateTime.of(2000,4,8,18,0,0)),
                ""
        );
        //when
        final Long timeDiffrenceInSec = eventTimeModel.getTimeDiffrenceInSec();
        //then
        final Long expected = 6*3600L + 3600*24*4;
        assertEquals(expected, timeDiffrenceInSec);
    }

}