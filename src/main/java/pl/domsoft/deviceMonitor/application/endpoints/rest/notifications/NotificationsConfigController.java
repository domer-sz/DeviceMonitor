package pl.domsoft.deviceMonitor.application.endpoints.rest.notifications;

import org.springframework.web.bind.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.BaseNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.EmailNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SMSNotificationsConfigCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationTargetModel;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums.NotificationTypeEnum;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories.EmailNotificationRepository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories.SMSNotificationRepository;

import java.util.List;

/**
 * Created by szymo on 21.08.2017.
 */
@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationsConfigController {

    private final CommandBus commandBus;
    private final EmailNotificationRepository emailNotificationRepository;
    private final SMSNotificationRepository smsNotificationRepository;

    public NotificationsConfigController(CommandBus commandBus, EmailNotificationRepository emailNotificationRepository, SMSNotificationRepository smsNotificationRepository) {
        this.commandBus = commandBus;
        this.emailNotificationRepository = emailNotificationRepository;
        this.smsNotificationRepository = smsNotificationRepository;
    }

    @PutMapping
    public boolean manageConfigs(@RequestBody BaseNotificationsConfigCommand config) throws AppException {
        commandBus.sendCommand(config);
        return true;
    }

    @GetMapping("/empty/email")
    public BaseNotificationsConfigCommand emptyEmailConfig(){
        return BaseNotificationsConfigCommand.getEmptyEmailConfigDto();
    }

    @GetMapping("/email")
    public EmailNotificationsConfigCommand getEmailConfig(){
        return emailNotificationRepository.findConfig().toModel();
    }

    @GetMapping("/empty/sms")
    public BaseNotificationsConfigCommand emptySMSConfig(){
        return BaseNotificationsConfigCommand.getEmptySMSConfigDto();
    }

    @GetMapping("/sms")
    public SMSNotificationsConfigCommand getSMSConfig(){
        return smsNotificationRepository.findConfig().toModel();
    }

    @GetMapping("/types")
    public List<NotificationTargetModel> getConfigTypes(){
        return NotificationTypeEnum.getTargetEmptyDtos();
    }
}
