package pl.domsoft.deviceMonitor.application.endpoints.rest.device;

import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.views.DeviceAccidentView;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.views.DeviceBreakView;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.views.DeviceEventView;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.views.DeviceOverviewView;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceEventType;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.views.DeviceAccidentViewRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.views.DeviceBreakViewRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.views.DeviceOverviewViewRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * Created by szymo on 14.05.2017.
 * Kontroler zwracający informacje o wydarzeniach urządzen
 */
@RestController
@RequestMapping("/api/deviceEvent")
@CrossOrigin(origins = "*")
public class DeviceEventController {

    private final DeviceBreakViewRepository deviceBreakRepository;
    private final DeviceOverviewViewRepository deviceOverviewRepository;
    private final DeviceAccidentViewRepository deviceAccidentRepository;


    public DeviceEventController(DeviceBreakViewRepository deviceBreakRepository, DeviceOverviewViewRepository deviceOverviewRepository, DeviceAccidentViewRepository deviceAccidentRepository) {
        this.deviceBreakRepository = deviceBreakRepository;
        this.deviceOverviewRepository = deviceOverviewRepository;
        this.deviceAccidentRepository = deviceAccidentRepository;
    }

    /**
     * Pobiera stronę z wpisami o awariach urządzeń
     * @param pageable
     * @return
     */
    @RequestMapping(path = "/accident", method = RequestMethod.GET)
    public Page<DeviceEventDecorator> getAccidents(Pageable pageable){
//        final Page<DeviceBreakView> breaksViews = deviceBreakRepository.findAll(pageable);
        final Page<DeviceAccidentView> accidentViews = deviceAccidentRepository.findAll(pageable);
        final Page<DeviceOverviewView> overviewViews = deviceOverviewRepository.findAll(pageable);
        final Page<DeviceBreakView> breaksViews = deviceBreakRepository.findAll(pageable);
        return convertPage(deviceAccidentRepository.findAll(pageable), pageable, DeviceAccidentView.class);
    }

    /**
     * Pobiera stronę z wpisami o awariach urządzeń
     * @param pageable
     * @return
     */
    @RequestMapping(path = "/accident/{deviceId}", method = RequestMethod.GET)
    public Page<DeviceEventDecorator> getAccidents(@PathVariable("deviceId") String deviceId, Pageable pageable){
        return convertPage(deviceAccidentRepository.findAllByDeviceId(deviceId, pageable), pageable, DeviceAccidentView.class);
    }

    /**
     * Pobiera stronę z wpisami o przerwach w działaniu urzadzeń
     * @param pageable
     * @return
     */
    @RequestMapping(path = "/break", method = RequestMethod.GET)
    public Page<DeviceEventDecorator> getBreaks(Pageable pageable){
        final Page<DeviceBreakView> all = deviceBreakRepository.findAll(pageable);
        final Page<DeviceEventDecorator> deviceEventDecorators = convertPage(all, pageable, DeviceBreakView.class);
        return deviceEventDecorators;
    }

    /**
     * Pobiera strone z przerwami danego urządzenia
     * @param deviceId
     * @param pageable
     * @return
     */
    @RequestMapping(path = "/break/{deviceId}", method = RequestMethod.GET)
    public Page<DeviceEventDecorator> getBreak(@PathVariable("deviceId") String deviceId, Pageable pageable){
        return convertPage(deviceBreakRepository.findAllByDeviceId(deviceId, pageable), pageable, DeviceBreakView.class);
    }

    /**
     * Pobiera stronę z wpisani wymaganuch przeglądów urządzeń
     * @param pageable
     * @return
     */
    @RequestMapping(path = "/overview", method = RequestMethod.GET)
    public Page<DeviceEventDecorator> getOverviews(Pageable pageable){
        return convertPage(deviceOverviewRepository.findAll(pageable), pageable, DeviceOverviewView.class);
    }

    /**
     * Pobiera stronę z wymaganymi przerwami dla danego urządzenia
     * @param deviceId
     * @param pageable
     * @return
     */
    @RequestMapping(path = "/overview/{deviceId}", method = RequestMethod.GET)
    public Page<DeviceEventDecorator> getOverviews(@PathVariable("deviceId") String deviceId, Pageable pageable){
        return convertPage(deviceOverviewRepository.findAllByDeviceId(deviceId, pageable), pageable, DeviceOverviewView.class);
    }

    private <T extends DeviceEventView> Page<DeviceEventDecorator> convertPage(Page<T> page, Pageable pageable, Class<T> clazz){
       return new PageImpl<>(
               page.getContent().stream().map(DeviceEventDecorator::new).collect(Collectors.toList()),
               pageable,
               page.getTotalElements()
       );
    }


    private class DeviceEventDecorator{

        private DeviceEventView deviceEvent;

        DeviceEventDecorator(DeviceEventView deviceEvent) {
            this.deviceEvent = deviceEvent;
        }

        public String getDeviceId(){
            return this.deviceEvent.getDeviceId();
        }

        public Date getStartDate(){
            return this.deviceEvent.getStartDate();
        }

        public Date getEndDate(){
           return this.deviceEvent.getEndDate();
        }

        public DeviceEventType getType(){
            return this.deviceEvent.getType();
        }

        public String getLocalisation(){
           return this.deviceEvent.getLocalisation();
        }


        public String getFormatedPeriod(){
            Validate.notNull(deviceEvent);
            Validate.notNull(deviceEvent.getStartDate());

            LocalDateTime localStar  = DateTimeUtils.dateToLocalDateTime(deviceEvent.getStartDate());
            LocalDateTime localEnd =
                    (deviceEvent.getEndDate() != null) ? DateTimeUtils.dateToLocalDateTime(deviceEvent.getEndDate()) :
                    LocalDateTime.now();
            final long until = localStar.until(localEnd, ChronoUnit.SECONDS);
            return DateTimeUtils.formatSecToDDHHMMSS(until);
        }


    }

}

