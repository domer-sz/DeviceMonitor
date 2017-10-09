package pl.domsoft.deviceMonitor.infrastructure.appstateholder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.BuildPublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.PublishCurrentDevicesStateCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceInformation;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceLogStateModel;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog.DeviceLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by szymo on 01.05.2017.
 * Component trzymający stan w scopie aplikacyjnym
 */
@Component
public class AppStateHolder implements ExceptionPersistable{
    private static final Logger log = LoggerFactory.getLogger(AppStateHolder.class);
    private final Map<String, DeviceState> deviceStateMap = new LinkedHashMap<>(100);
    private final CommandBus commandBus;
    private final DeviceLogRepository queryDeviceLogRepo;
    private final DeviceConfigRepository deviceConfigRepository;
    private final AppExceptionRepository appExceptionRepository;


    public AppStateHolder(CommandBus commandBus, DeviceLogRepository queryDeviceLogRepo, DeviceConfigRepository deviceConfigRepository, AppExceptionRepository appExceptionRepository) {
        this.commandBus = commandBus;
        this.queryDeviceLogRepo = queryDeviceLogRepo;
        this.deviceConfigRepository = deviceConfigRepository;
        this.appExceptionRepository = appExceptionRepository;
    }

    @PostConstruct
    private void postConstruct(){
        LocalDateTime currentTimestamp = DateTimeUtils.getCurrentLocalDateTime();
        Collection<DeviceLogStateModel> lastLogs = queryDeviceLogRepo.findLastLogs();
        final List<String> allConfiguredDeviceIds = deviceConfigRepository.findAllReturnDeviceIds();
        try {
            PublishCurrentDevicesStateCommand publishStateCommand = commandBus.sendCommand(
                    new BuildPublishCurrentDevicesStateCommand(
                            lastLogs,
                            currentTimestamp,
                            allConfiguredDeviceIds
                    ),
                    PublishCurrentDevicesStateCommand.class
            );
            updateDevicesStates(publishStateCommand);
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    /**
     * pobiera stan urządzenia z apisany w aplikaji
     * @param deviceId - id urządzenia któego stanu szukamy
     * @return stan urządzenia zapisany w aplikacji
     * @throws AppException - gdy stan urządzenia o szukanym id nie był nigdy zapisany rzuca wyjątkiem
     */
    public DeviceState getStateForDeviceThrowable(String deviceId) throws AppException{
        try{
            return deviceStateMap.get(deviceId);
        }catch (Exception e){
            throw new AppException("W aplikacji nie ma obecnie stanu dla urządzenia o id: " + deviceId, e, getExcpRep());
        }
    }

    /**
     * pobiera stan urządzenia z apisany w aplikaji
     * @param deviceId - id urządzenia któego stanu szukamy
     * @return stan urządzenia zapisany w aplikacji, gdy nie znaleziono stanu zwraca null
     */
    public DeviceState getStateForDeviceNullable(String deviceId){
        try{
            return deviceStateMap.get(deviceId);
        }catch (Exception e){
            e.printStackTrace();
            final List<DeviceState> lastLogs = queryDeviceLogRepo.findLastLogs(deviceId, 1);
            if(!lastLogs.isEmpty()){
                return  lastLogs.get(0);
            }
            return null;
        }
    }

    public Set<DeviceInformation> getCurrentState(){
        Set<DeviceState> states;
        states = deviceStateMap.entrySet().stream().map( entry -> entry.getValue()).collect(Collectors.toSet());
        Set<DeviceInformation> deviceInfos = new LinkedHashSet<>(states.size());
        for (DeviceState state: states) {
            final DeviceInformation deviceInformation = DeviceInformation.build(state, deviceConfigRepository);
            if(deviceInformation != null)
            deviceInfos.add(deviceInformation);
        }

        return deviceInfos;
    }

    public void updateDeviceState(DeviceState newState){

        if(deviceStateMap.containsKey(newState.getDeviceId())){
            DeviceState stateForDevice = this.getStateForDeviceNullable(newState.getDeviceId());
                deviceStateMap.replace(stateForDevice.getDeviceId(), newState);
//                log.info("Podmieniono stan urządzenia z : " + stateForDevice + " na " + newState);
        }else{
            deviceStateMap.put(newState.getDeviceId(), newState);
//            log.info("Dodano nowy stan: " + newState);
        }
    }

    private void updateDevicesStates(Collection<DeviceState> newStates) throws AppException {
        log.info("Odświerzam stany urządzeń w AppStateHolderze");
        for (DeviceState state: newStates) {
            updateDeviceState(state);
        }
    }

    private void updateDevicesStates(PublishCurrentDevicesStateCommand command) throws AppException {
        updateDevicesStates(command.getDeviceStates());
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
