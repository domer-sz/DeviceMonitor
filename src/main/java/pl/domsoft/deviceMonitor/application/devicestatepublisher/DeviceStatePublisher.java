package pl.domsoft.deviceMonitor.application.devicestatepublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.appconfig.StaticFinalConfigProvider;
import pl.domsoft.deviceMonitor.infrastructure.appstateholder.AppStateHolder;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.PublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.UpdateDeviceEventsCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;

import java.util.Random;

/**
 * Created by szymo on 30.04.2017.
 *
 */
@Component
public class DeviceStatePublisher {

    private final AppStateHolder stateHolder;
    private final CommandBus commandBus;

    @Autowired
    private SimpMessagingTemplate template;

    @Value("${device.log.ws.topic.name}")
    private String wsTopicName;

    @Value("${device.log.ws.topic.suffix.name}")
    private String wsTopicSuffixName;

    public DeviceStatePublisher(AppStateHolder stateHolder, CommandBus commandBus) {
        this.stateHolder = stateHolder;
        this.commandBus = commandBus;
    }

    @JmsListener(destination = StaticFinalConfigProvider.JMS_DEST_DEVICE_STATE_PUBLISHER, containerFactory = StaticFinalConfigProvider.JMS_CONTAINER_FACTORY)
    public void receiveMessage(PublishCurrentDevicesStateCommand command) {
        int i = 0;
        for(DeviceState newCalculatedState: command.getDeviceStates()){
            final DeviceState savedState = stateHolder.getStateForDeviceNullable(newCalculatedState.getDeviceId());
            //Wysłanie komendy aktualzującej wpisy wydarzeń urządzenia
            try {
                commandBus.sendCommand(new UpdateDeviceEventsCommand(savedState, newCalculatedState));
            } catch (AppException e) {
                e.printStackTrace();
            }
            //aktualizacja danych o urządzeniu w stateHolderze
            stateHolder.updateDeviceState(newCalculatedState);

        }

        this.template.convertAndSend(wsTopicName+wsTopicSuffixName, command);

    }
}
