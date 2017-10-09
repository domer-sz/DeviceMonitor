package pl.domsoft.deviceMonitor.infrastructure.shared.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.domsoft.deviceMonitor.appconfig.StaticFinalConfigProvider;
import pl.domsoft.deviceMonitor.application.deviceloganalyzer.DeviceLogAnalyzer;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by szymo on 23.04.2017.
 * Utility do konwersji dat i czasu
 */
public class DateTimeUtils {

    private static final Logger log = LoggerFactory.getLogger(DeviceLogAnalyzer.class);


    public static LocalDate dateToLocalDate(Date date){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime dateToLocalTime(Date date){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        if(date == null) return null;
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime ldt){
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date localDateToDate(LocalDate ld){
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date getCurrentDateTime(){
        return DateTimeUtils.localDateTimeToDate(DateTimeUtils.getCurrentLocalDateTime());
    }

    public static LocalDateTime getCurrentLocalDateTime(){
        if(!StaticFinalConfigProvider.USE_FAKE_DATE){
            log.info("Pobrałeś faktyczną date teraźniejszą: " + LocalDateTime.now());
            return LocalDateTime.now();
        }
        { LocalDateTime customCurrentDate = LocalDateTime.parse(StaticFinalConfigProvider.FAKE_CURRENT_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            log.info("Pobrałeś sztuczną datę teraźniejszą: " + customCurrentDate);
            return customCurrentDate;
        }
    }

    public static LocalTime getCurrentLocalTime(){
        if(!StaticFinalConfigProvider.USE_FAKE_DATE){
            log.info("Pobrałeś faktyczną date teraźniejszą: " + LocalDateTime.now());
            return LocalDateTime.now().toLocalTime();
        }
        { LocalDateTime customCurrentDate = LocalDateTime.parse(StaticFinalConfigProvider.FAKE_CURRENT_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            log.info("Pobrałeś sztuczną datę teraźniejszą: " + customCurrentDate);
            return customCurrentDate.toLocalTime();
        }
    }

    /** Metoda przyjmuje ilość sekund i zwraca ilość dni, godzin, minut i sekund odpowiadająca ilości tych sekund
     * w formacie DD D HH H MM m SS s
     * @param seconds
     * @return
     */
    public static String formatSecToDDHHMMSS(long seconds){
        double d = seconds*1.0 / (3600*24);
        long dd = (long) Math.floor(d);
        seconds = seconds - (dd*3600*24);
        double h = seconds*1.0 / (3600);
        long hh = (long) Math.floor(h);
        seconds = seconds - (hh*3600);
        double m = seconds*1.0 / (60);
        long mm = (long) Math.floor(m);
        seconds = seconds - mm * (60);

        return dd + "D " + hh + "H " + mm + "m " + seconds + "s ";
    }

}
