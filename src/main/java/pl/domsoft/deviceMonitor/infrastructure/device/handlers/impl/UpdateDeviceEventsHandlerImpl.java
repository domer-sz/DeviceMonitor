package pl.domsoft.deviceMonitor.infrastructure.device.handlers.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.PublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.UpdateDeviceEventsCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.UpdateDeviceEventsHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;
import pl.domsoft.deviceMonitor.infrastructure.device.model.EventsGather;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceEventRepository;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.commands.SendSMSNotificationCommand;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms.SMSNotificationConfig;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.sms.SMSTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.repositories.SMSNotificationRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by szymo on 23.04.2017.
 * Implementacja handlera komendy {@link PublishCurrentDevicesStateCommand}
 */
@Component
class UpdateDeviceEventsHandlerImpl implements UpdateDeviceEventsHandler {

    private static final Logger log = LoggerFactory.getLogger(UpdateDeviceEventsHandlerImpl.class);


    private final DeviceEventRepository deviceEventRepository;
    private final DeviceConfigRepository deviceConfigRepository;
    private final SMSNotificationRepository smsNotificationRepository;
    private final CommandBus commandBus;

    @Value("${send.sms.to.device.owner}")
    boolean sendSmsToDeviceOwner;

    UpdateDeviceEventsHandlerImpl(DeviceEventRepository deviceEventRepository, DeviceConfigRepository deviceConfigRepository, SMSNotificationRepository smsNotificationRepository, CommandBus commandBus) {
        this.deviceEventRepository = deviceEventRepository;
        this.deviceConfigRepository = deviceConfigRepository;
        this.smsNotificationRepository = smsNotificationRepository;
        this.commandBus = commandBus;
    }

    @Override
    public Void handle(UpdateDeviceEventsCommand command) {
            DeviceState currentState = command.getNewState();

        manageDeviceBreaks(currentState);
        manageDeviceAccidents(currentState);
        manageDeviceOverviews(currentState);
        return null;
    }

    private void manageDeviceOverviews(DeviceState currentState) {
        final DeviceOverview lastDeviceOverview = deviceEventRepository.findLastOpenDeviceOverview(currentState.getDeviceId());
        //jeśli obecny stan to "off"
        if(currentState.overviewNeeded()){
            // i jeśli nie ma już otworzonej przerwy dodaj nową
            if(lastDeviceOverview == null){
                DeviceOverview deviceOverview = new DeviceOverview(
                        currentState.getDeviceId(),
                        DateTimeUtils.getCurrentDateTime(),
                        null,
                        ""
                );
                deviceEventRepository.save(deviceOverview);
            }
        }else{
            //jeśli obecny stan to "on" to sprawdź czy jest otwarta przerwa i jeśli jest to ją zamknij
            if(lastDeviceOverview != null){
                lastDeviceOverview.setEndDate(DateTimeUtils.getCurrentDateTime());
                deviceEventRepository.save(lastDeviceOverview);
            }
        }
    }

    private void manageDeviceAccidents(DeviceState currentState) {
        final DeviceAccident lastDeviceAccident = deviceEventRepository.findLastOpenDeviceAccident(currentState.getDeviceId());
        //jeśli obecny stan to "off"
        if(currentState.warrningAlert()){
            // i jeśli nie ma już otworzonej przerwy dodaj nową
            if(lastDeviceAccident == null){
                DeviceAccident deviceAccident = new DeviceAccident(
                        currentState.getDeviceId(),
                        DateTimeUtils.getCurrentDateTime(),
                        null,
                        ""
                );
                deviceEventRepository.save(deviceAccident);
                sendNotificationToDeviceOwner(deviceAccident);
            }
        }else{
            //jeśli obecny stan to "on" to sprawdź czy jest otwarta przerwa i jeśli jest to ją zamknij
            if(lastDeviceAccident != null){
                lastDeviceAccident.setEndDate(DateTimeUtils.getCurrentDateTime());
                deviceEventRepository.save(lastDeviceAccident);
            }
        }
    }

    private void manageDeviceBreaks(DeviceState currentState) {
        final DeviceBreak lastDeviceBreak = deviceEventRepository.findLastOpenDeviceBreak(currentState.getDeviceId());
        //jeśli obecny stan to "off"
        if(currentState.isOff()){
            // i jeśli nie ma już otworzonej przerwy dodaj nową
            if(lastDeviceBreak == null){
                DeviceBreak deviceBreak = new DeviceBreak(
                        currentState.getDeviceId(),
                        DateTimeUtils.getCurrentDateTime(),
                        null,
                        ""
                );
                deviceEventRepository.save(deviceBreak);
                sendNotificationToDeviceOwner(deviceBreak);
            }
        }else{
            //jeśli obecny stan to "on" to sprawdź czy jest otwarta przerwa i jeśli jest to ją zamknij
            if(lastDeviceBreak != null){
                lastDeviceBreak.setEndDate(DateTimeUtils.getCurrentDateTime());
                deviceEventRepository.save(lastDeviceBreak);
            }
        }
    }

    private void sendNotificationToDeviceOwner(DeviceBreak deviceBreak) {
        if(sendSmsToDeviceOwner){
            List<SMSTarget> targets = prepareTargetsList(deviceBreak);
            for (SMSTarget target: targets) {
                final SendSMSNotificationCommand sendSMSNotificationCommand = buildSendSMSNotificationCommand(deviceBreak, target);
                try {
                    commandBus.sendCommand(sendSMSNotificationCommand, Boolean.class);
                } catch (AppException e) {
                    log.error(e.getStackInfo());
                }
            }
        }

    }

    private void sendNotificationToDeviceOwner(DeviceAccident deviceAccident) {
        if(sendSmsToDeviceOwner) {
            List<SMSTarget> targets = prepareTargetsList(deviceAccident);
            for (SMSTarget target : targets) {
                final SendSMSNotificationCommand sendSMSNotificationCommand = buildSendSMSNotificationCommand(deviceAccident, target);
                try {
                    commandBus.sendCommand(sendSMSNotificationCommand, Boolean.class);
                } catch (AppException e) {
                    log.error(e.getStackInfo());
                }
            }
        }
    }

    private SendSMSNotificationCommand buildSendSMSNotificationCommand(DeviceBreak deviceBreak, SMSTarget target) {
        return new SendSMSNotificationCommand(
                target,
                new EventsGather(
                        Collections.singletonList(deviceBreak), Collections.emptyList(), Collections.emptyList()
                )
        );
    }

    private SendSMSNotificationCommand buildSendSMSNotificationCommand(DeviceAccident deviceAccident, SMSTarget target) {
        return new SendSMSNotificationCommand(
                target,
                new EventsGather(
                        Collections.emptyList(), Collections.singletonList(deviceAccident), Collections.emptyList()
                )
        );
    }

    private List<SMSTarget> prepareTargetsList(DeviceBreak deviceBreak) {
        List<SMSTarget> targets = new ArrayList<>();
        final DeviceConfig config = deviceConfigRepository.findFirstDeviceConfigByDeviceId(deviceBreak.getDeviceId());
        targets.add(new SMSTarget(config.getContact().getPhoneNumber()));
        final SMSNotificationConfig smsNotificationConfig = smsNotificationRepository.findConfig();
        for ( SMSTarget smsTarget : smsNotificationConfig.getSmsTargets()) {
            if(smsTarget.sendOnBreak()){
                targets.add(smsTarget);
            }
        }
        return targets;
    }

    private List<SMSTarget> prepareTargetsList(DeviceAccident deviceAccident) {
        List<SMSTarget> targets = new ArrayList<>();
        final DeviceConfig config = deviceConfigRepository.findFirstDeviceConfigByDeviceId(deviceAccident.getDeviceId());
        targets.add(new SMSTarget(config.getContact().getPhoneNumber()));
        final SMSNotificationConfig smsNotificationConfig = smsNotificationRepository.findConfig();
        for ( SMSTarget smsTarget : smsNotificationConfig.getSmsTargets()) {
            if(smsTarget.sendOnAccident()){
                targets.add(smsTarget);
            }
        }
        return targets;
    }
}
