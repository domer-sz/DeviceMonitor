package pl.domsoft.deviceMonitor.infrastructure.shared.utils;

import org.junit.Ignore;
import org.junit.Test;
import pl.smsapi.Client;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.api.response.MessageResponse;
import pl.smsapi.api.response.SendStatusResponse;
import pl.smsapi.api.response.StatusResponse;
import pl.smsapi.exception.ActionException;
import pl.smsapi.exception.ClientException;
import pl.smsapi.exception.HostException;
import pl.smsapi.exception.SmsapiException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by szymo on 06.06.2017.
 * Test jednostkowy metod przeliczających daty
 */
public class DateTimeUtilsTest {


    @Test
    public void dateToLocalDate() throws Exception {
        //given
        //1900-1-1
        final Date date1 = new Date(0, 0, 1, 20, 23, 43);
        final Date date2 = new Date(0, 0, 1, 0, 0, 0);
        final Date date3 = new Date(0, 0, 1, 23, 59, 59);

        //when
        final LocalDate convertedDate1 = DateTimeUtils.dateToLocalDate(date1);
        final LocalDate convertedDate2 = DateTimeUtils.dateToLocalDate(date2);
        final LocalDate convertedDate3 = DateTimeUtils.dateToLocalDate(date3);

        //then
        final LocalDate expectedData = LocalDate.of(1900, 1, 1);
        assertEquals(expectedData, convertedDate1);
        assertEquals(expectedData, convertedDate2);
        assertEquals(expectedData, convertedDate3);
    }

    @Test
    public void dateToLocalTime() throws Exception {
        //given
        final Date date1 = new Date(0, 0, 1, 20, 23, 43);
        final Date date2 = new Date(200, 0, 1, 23, 59, 59);
        final Date date3 = new Date(0, 5, 1, 23, 59, 59);

        //when
        final LocalTime convertedDate1 = DateTimeUtils.dateToLocalTime(date1);
        final LocalTime convertedDate2 = DateTimeUtils.dateToLocalTime(date2);
        final LocalTime convertedDate3 = DateTimeUtils.dateToLocalTime(date3);

        //then
        final LocalTime expectedTime = LocalTime.of(23, 59, 59);
        assertNotEquals(expectedTime, convertedDate1);
        assertEquals(expectedTime, convertedDate2);
        assertEquals(expectedTime, convertedDate3);
    }

    @Test
    public void dateToLocalDateTime() throws Exception {
        //given
        final Date date1 = new Date(0, 0, 1, 20, 23, 43);
        final Date date2 = new Date(200, 0, 1, 23, 59, 59);
        final Date date3 = new Date(0, 5, 1, 23, 59, 59);

        //when
        final LocalDateTime convertedDate1 = DateTimeUtils.dateToLocalDateTime(date1);
        final LocalDateTime convertedDate2 = DateTimeUtils.dateToLocalDateTime(date2);
        final LocalDateTime convertedDate3 = DateTimeUtils.dateToLocalDateTime(date3);

        //then
        assertEquals(LocalDateTime.of(1900,1,1,20,23,43), convertedDate1);
        assertEquals(LocalDateTime.of(2100,1,1,23,59,59), convertedDate2);
        assertEquals(LocalDateTime.of(1900,6,1,23,59,59), convertedDate3);
    }

    @Test
    public void localDateTimeToDate() throws Exception {
        //given
        final LocalDateTime ldt1 = LocalDateTime.of(1900, 1, 1, 20, 23, 43);
        final LocalDateTime ldt2 = LocalDateTime.of(2100, 1, 1, 23, 59, 59);
        final LocalDateTime ldt3 = LocalDateTime.of(1900, 6, 1, 23, 59, 59);
        //when
        final Date convertedDate1 = DateTimeUtils.localDateTimeToDate(ldt1);
        final Date convertedDate2 = DateTimeUtils.localDateTimeToDate(ldt2);
        final Date convertedDate3 = DateTimeUtils.localDateTimeToDate(ldt3);


        //then
        final Date date1 = new Date(0, 0, 1, 20, 23, 43);
        final Date date2 = new Date(200, 0, 1, 23, 59, 59);
        final Date date3 = new Date(0, 5, 1, 23, 59, 59);
        assertEquals(date1, convertedDate1);
        assertEquals(date2, convertedDate2);
        assertEquals(date3, convertedDate3);
    }

    @Test
    public void localDateToDate() throws Exception {
        //given
        //1900-1-1
        final LocalDate date1 = LocalDate.of(1900, 1, 1);

        //when
        final Date convertedDate1 = DateTimeUtils.localDateToDate(date1);
        //then

        final Date expectedDate = new Date(0, 0, 1, 0, 0, 0);
        assertEquals(expectedDate, convertedDate1);

    }

    @Test
    public void formatSecToDDHHMMSS() throws Exception {
        //given
        long seconds = 3600*24*3L+3666;
       //when
        final String breaksTimeFormated = DateTimeUtils.formatSecToDDHHMMSS(seconds);
        //then
        assertEquals("3D 1H 1m 6s ", breaksTimeFormated);
    }
}