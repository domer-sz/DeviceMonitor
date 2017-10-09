package pl.domsoft.deviceMonitor.application.endpoints.rest.generatedata;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.GenerateDeviceConfigsCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.GenerateDeviceLogsCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.time.LocalDate;


/**
 * Created by szymo on 22.04.2017.
 */
@RestController
@RequestMapping(path = "/api/admin")
public class GenerateController {

    private final CommandBus commandBus;
    private final DeviceConfigRepository queryDeviceConfigRepository;


    public GenerateController(CommandBus commandBus, DeviceConfigRepository queryDeviceConfigRepository) {
        this.commandBus = commandBus;
        this.queryDeviceConfigRepository = queryDeviceConfigRepository;
    }
    @GetMapping("/test")
    public String elo(){return "elo";}

    @RequestMapping("/generateConfigs/{generateAmount}")
    public String generateConfigs(@PathVariable(value = "generateAmount") int generateAmount) throws AppException {
            String response = null;

            response = commandBus.sendCommand(new GenerateDeviceConfigsCommand(generateAmount), String.class);
            return response;

    }


    @RequestMapping("/generateLogs")
    public String generateLogs(){
        String response = null;
        //2016-01-31 20:46:00
        //2016-03-04 14:55:00
        try {
            response = commandBus.sendCommand(
                    new GenerateDeviceLogsCommand(
                        DateTimeUtils.localDateToDate(LocalDate.of(2016, 1, 30)),
                            DateTimeUtils.localDateToDate(LocalDate.of(2016, 3, 5)),
                        600,
                        queryDeviceConfigRepository.findAllReturnDeviceIds()
                    ),
                    String.class);
            return response;
        } catch (AppException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

}
