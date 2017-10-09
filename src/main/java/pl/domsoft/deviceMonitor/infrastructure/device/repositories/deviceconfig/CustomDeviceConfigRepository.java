package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by szymo on 23.04.2017.
 * interfejs rozszerzający repozytorium o dodatkowe metody z własną implementacją
 */
interface CustomDeviceConfigRepository {
    @Transactional(readOnly = true)
    List<String> findAllReturnDeviceIds();

}
