package pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by szymo on 03.05.2017.
 * implementacja rozszeżająca podstawowe repozytorium
 */
class DeviceEventRepositoryImpl implements CustomDeviceEventRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public DeviceBreak findLastOpenDeviceBreak(String deviceId) {
        TypedQuery<DeviceBreak> query = em.createNamedQuery(DeviceBreak.Q_FIND_LAST_OPEN_BREAK_FOR_DEVICE, DeviceBreak.class);
        query.setParameter("deviceId", deviceId);
        query.setMaxResults(1);
        final List<DeviceBreak> resultList = query.getResultList();
        if(resultList.isEmpty()){
            return null;
        }else{
            return resultList.get(0);
        }
    }

    @Override
    public DeviceAccident findLastOpenDeviceAccident(String deviceId) {
        TypedQuery<DeviceAccident> query = em.createNamedQuery(DeviceAccident.Q_FIND_LAST_OPEN_ACCIDENT_FOR_DEVICE, DeviceAccident.class);
        query.setParameter("deviceId", deviceId);
        query.setMaxResults(1);
        final List<DeviceAccident> resultList = query.getResultList();
        if(resultList.isEmpty()){
            return null;
        }else{
            return resultList.get(0);
        }
    }

    @Override
    public DeviceOverview findLastOpenDeviceOverview(String deviceId) {
        TypedQuery<DeviceOverview> query = em.createNamedQuery(DeviceOverview.Q_FIND_LAST_OPEN_OVERVIEW_FOR_DEVICE, DeviceOverview.class);
        query.setParameter("deviceId", deviceId);
        query.setMaxResults(1);
        final List<DeviceOverview> resultList = query.getResultList();
        if(resultList.isEmpty()){
            return null;
        }else{
            return resultList.get(0);
        }
    }
}
