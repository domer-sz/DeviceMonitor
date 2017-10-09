package pl.domsoft.deviceMonitor.appconfig.websockets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{


    @Value("${device.log.ws.app.prefix}")
    private String wsAppPrefix;

    @Value("${device.log.ws.topic.name}")
    private String wsTopicName;

    @Value("${device.log.ws.endpoint.name}")
    private String wsEndpointName;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(wsTopicName);
        config.setApplicationDestinationPrefixes(wsAppPrefix);


    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(wsEndpointName).setAllowedOrigins("*").withSockJS();
    }

}