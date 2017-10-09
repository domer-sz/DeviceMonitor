package pl.domsoft.deviceMonitor.infrastructure.device.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Localisation;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;

import java.util.Date;

/**
 * Created by szymo on 14.05.2017.
 * Obiekt DTO zwracający podstawowe informacjie o konfiguracji urzadzenia
 */
public class DeviceInformation extends DeviceState {

    private final Localisation localisation;

    private DeviceInformation(){
        super(null,false,false,false,0,null);
        localisation = null;
    }

    @JsonCreator
    public DeviceInformation(
            @JsonProperty("deviceId") String deviceId,
            @JsonProperty("on") boolean on,
            @JsonProperty("overview") boolean overview,
            @JsonProperty("warning") boolean warning,
            @JsonProperty("deviceTimeOn") int deviceTimeOn,
            @JsonProperty("logDate") Date logDate,
            @JsonProperty("localisation") Localisation localisation
    ){
        super(deviceId, on, overview, warning, deviceTimeOn, logDate);
        this.localisation = localisation;
    }

    public Localisation getLocalisation() {
        return localisation;
    }

    public static DeviceInformation build(DeviceState state, DeviceConfigRepository deviceConfigRepository){
        DeviceConfig config = deviceConfigRepository.findFirstDeviceConfigByDeviceId(state.getDeviceId());
       if(config != null) {

           return new DeviceInformation(
                   state.getDeviceId(),
                   state.isOn(),
                   state.overviewNeeded(),
                   state.warrningAlert(),
                   state.getDeviceTimeOn(),
                   state.getLogDate(),
                   config.getLocalisation()
           );
       }else {
           return null;
       }
    }
}
