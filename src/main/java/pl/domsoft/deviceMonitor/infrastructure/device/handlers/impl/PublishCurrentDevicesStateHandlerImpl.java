package pl.domsoft.deviceMonitor.infrastructure.device.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.appconfig.StaticFinalConfigProvider;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.PublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.PublishCurrentDevicesStateHandler;

/**
 * Created by szymo on 23.04.2017.
 * Implementacja handlera komendy {@link PublishCurrentDevicesStateCommand}
 */
@Component
class PublishCurrentDevicesStateHandlerImpl implements PublishCurrentDevicesStateHandler {

    @Autowired
    private JmsTemplate jmsTemplate ;
//
//    @Autowired
//    public PublishCurrentDevicesStateHandlerImpl(JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//    }

    @Override //odbiorca jest DeviceStatePublisher
    public Void handle(PublishCurrentDevicesStateCommand command) {
        jmsTemplate.convertAndSend(StaticFinalConfigProvider.JMS_DEST_DEVICE_STATE_PUBLISHER, command);
        return null;
    }
}
