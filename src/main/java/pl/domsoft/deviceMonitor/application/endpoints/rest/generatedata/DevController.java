package pl.domsoft.deviceMonitor.application.endpoints.rest.generatedata;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceEventRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog.DeviceLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.BaseNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationTargetModel;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums.NotificationTypeEnum;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.commands.GenerateStateReportCommand;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.report.ReportSpreadsheetModel;
import pl.domsoft.deviceMonitor.infrastructure.user.account.repositories.query.QueryAccountRepository;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities.CustomLogFieldConfig;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.repositories.CustomLogFieldConfigRepository;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by szymo on 30.04.2017.
 * Kontroler developerski
 */
@RestController
@RequestMapping(path = "/api/admin/dev")
@CrossOrigin(origins = "*")
public class DevController {


    private final CommandBus commandBus;
    private final DeviceEventRepository deviceEventRepository;
    private final DeviceConfigRepository deviceConfigRepository;
    private final DeviceLogRepository deviceLogRepository;
    private final CustomLogFieldConfigRepository customLogFieldConfigRepository;
    private final QueryAccountRepository accountRepository;

    public DevController(CommandBus commandBus, DeviceEventRepository deviceEventRepository, DeviceConfigRepository deviceConfigRepository, DeviceLogRepository deviceLogRepository, CustomLogFieldConfigRepository customLogFieldConfigRepository, QueryAccountRepository accountRepository) {
        this.commandBus = commandBus;
        this.deviceEventRepository = deviceEventRepository;
        this.deviceConfigRepository = deviceConfigRepository;
        this.deviceLogRepository = deviceLogRepository;
        this.customLogFieldConfigRepository = customLogFieldConfigRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public String post(@RequestBody BaseNotificationsConfigCommand c) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        System.out.println(sdf.parse(c.getTime()));
        return c.toString();
    }

    @GetMapping("/echo/{echo}")
    public List<NotificationTargetModel> echo(@PathVariable("echo") String echo){

        return NotificationTypeEnum.getTargetEmptyDtos();
    }

    @GetMapping("/generateLogSettings")
    public String generateLogSettigns(){
        CustomLogFieldConfig natlenienie = new CustomLogFieldConfig("Natlenienie", "analog_in1");
        CustomLogFieldConfig stanPompy = new CustomLogFieldConfig("Stan pompy", "digital_in2");
        CustomLogFieldConfig ph = new CustomLogFieldConfig("pH", "analog_in3");
        int i = 0;
        if(!customLogFieldConfigRepository.findOneByViewName("Natlenienie").isPresent()){
            i++;
            customLogFieldConfigRepository.save(natlenienie);
        }
        if(!customLogFieldConfigRepository.findOneByViewName("Stan pompy").isPresent()){
            i++;
            customLogFieldConfigRepository.save(stanPompy);
        }
        if(!customLogFieldConfigRepository.findOneByViewName("pH").isPresent()){
            i++;
            customLogFieldConfigRepository.save(ph);
        }

        return "Ok, dodałem " + i + " konfiguracji";
    }

    @GetMapping("")
    @Transactional
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private ReportSpreadsheetModel genDate() throws AppException {
        accountRepository.findAll();
//        final Collection<DeviceEventNotification> deviceEvents = deviceEventRepository.findAll();
//        for (DeviceEventNotification event: deviceEvents) {
//            event.setStartDate(DateTimeUtils.localDateTimeToDate(DateTimeUtils.dateToLocalDateTime(event.getStartDate()).minusMinutes(ThreadLocalRandom.current().nextInt(600, 3600+ 1))));
//            event.setEndDate(DateTimeUtils.localDateTimeToDate(DateTimeUtils.dateToLocalDateTime(event.getStartDate()).plusMinutes(ThreadLocalRandom.current().nextInt(10, 600+ 1))));
//            deviceEventRepository.save(event);
//        }

//        final Collection<DeviceConfig> deviceConfigs = deviceConfigRepository.findAll();
//        for (DeviceConfig dc : deviceConfigs) {
//            if(dc.getLocalisation().getApartmentNumber().equals("xx")) dc.getLocalisation().setApartmentNumber("");
//            dc.setDataReadingConfiguration("");
//            deviceConfigRepository.save(dc);
//        }

//        final Collection<DeviceLog> logs = deviceLogRepository.findAll();
//        for (DeviceLog log: logs ) {
//            log.setTimeOnCount(ThreadLocalRandom.current().nextInt(900000, 1500000+ 1));
//            deviceLogRepository.save(log);
//        }

        //2016-01-31 20:46:00        //2016-16-04 14:55:00

        GenerateStateReportCommand command = new GenerateStateReportCommand(
                DateTimeUtils.localDateTimeToDate(LocalDateTime.of(2016,1,31,20,46,0)),
                DateTimeUtils.localDateTimeToDate(LocalDateTime.of(2016,3,4,5,19,0)),
                new ArrayList<String>(Arrays.asList("dev_1", "dev_2", "dev_3"))
        );

        final ReportSpreadsheetModel model = commandBus.sendCommand(command, ReportSpreadsheetModel.class);
        return model;
    }


}
