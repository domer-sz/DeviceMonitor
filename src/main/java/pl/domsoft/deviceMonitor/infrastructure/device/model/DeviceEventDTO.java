package pl.domsoft.deviceMonitor.infrastructure.device.model;

import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceEvent;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import java.time.LocalDateTime;

/**
 * Created by szymo on 14.05.2017.
 */
public class DeviceEventDTO {
    /**
     * id encji
     */
    protected final Long id;
    /**
     * Id urządzenia którego dotyczy zdarzenie
     */
    protected final String deviceId;

    /**
     * Początek zdarzenia
     */
    protected final LocalDateTime startDate;

    /**
     * Koniec zdarzenia
     */
    protected final LocalDateTime endDate;

    /**
     * Miejsce na komentarz dla danej zdarzenia
     */
    protected final String comment;

    protected final DeviceEventType deviceEventType;

    public DeviceEventDTO(DeviceEvent event){
        this.id = event.getId();
        this.deviceId = event.getDeviceId();
        this.startDate = DateTimeUtils.dateToLocalDateTime(event.getStartDate());
        this.endDate = DateTimeUtils.dateToLocalDateTime(event.getEndDate());
        this.comment = event.getComment();
        this.deviceEventType = event.getType();
    }

    public Long getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getComment() {
        return comment;
    }
}
