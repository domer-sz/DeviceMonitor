package pl.domsoft.deviceMonitor.infrastructure.device.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.GenerateDeviceConfigsCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Contact;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Localisation;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.GenerateDeviceConfigsHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by szymo on 22.04.2017.
 * Implementacja handlera komendy {@link GenerateDeviceConfigsCommand}
 */
@Component
class GenerateDeviceConfigsHandlerImpl implements GenerateDeviceConfigsHandler {

    @Autowired
    private DeviceConfigRepository deviceConfigRepository;


    private final Supplier<DeviceConfig> generateDeviceConf = () -> new DeviceConfig(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        new Localisation("", "", "Bydgoszcz", "85-980", "Fordońska", "xx", "xx"),
        new Contact("viwern@gmail.com", "666777888", true)
    );

    public GenerateDeviceConfigsHandlerImpl(){
    }

    @Override
    public String handle(GenerateDeviceConfigsCommand command) {
        List<DeviceConfig> deviceConfigs = IntStream.range(0, command.getNumberOfConfigsToGenerate()).boxed()
                .map(i -> generateDeviceConf.get())
                .collect(Collectors.toList());

        deviceConfigRepository.save(deviceConfigs);
        return "Pomyślnie wygenerowano " + command.getNumberOfConfigsToGenerate() + " konfiguracji";
    }
}
