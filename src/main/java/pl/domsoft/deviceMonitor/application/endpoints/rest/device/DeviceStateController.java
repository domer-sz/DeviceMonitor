package pl.domsoft.deviceMonitor.application.endpoints.rest.device;

import org.springframework.web.bind.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.appstateholder.AppStateHolder;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceFullData;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceInformation;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog.DeviceLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.model.pairs.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by szymo on 14.05.2017.
 * Kontroler odpowiedzialny za podanie pierwszej paczki danych po starcie aplikacji
 */
@RestController
@RequestMapping("/api/deviceState")
@CrossOrigin(origins = "*")
public class DeviceStateController {

    private final AppStateHolder stateHolder;
    private final DeviceLogRepository deviceLogRepository;

    public DeviceStateController(AppStateHolder stateHolder, DeviceLogRepository deviceLogRepository) {
        this.stateHolder = stateHolder;
        this.deviceLogRepository = deviceLogRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "")
    public Set<DeviceInformation> getCurrentDeviceStates(){
        return stateHolder.getCurrentState();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{deviceId}/{statesNumber}")
    public List<DeviceState> getLastStatesForDevice(@PathVariable("deviceId") String deviceId, @PathVariable("statesNumber") int statesNumber){
        return deviceLogRepository.findLastLogs(deviceId, statesNumber);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{deviceId}/data")
    public DeviceFullData getLastDataForDevice(@PathVariable("deviceId") String deviceId){
        return deviceLogRepository.findLastDeviceData(deviceId);
    }


}
