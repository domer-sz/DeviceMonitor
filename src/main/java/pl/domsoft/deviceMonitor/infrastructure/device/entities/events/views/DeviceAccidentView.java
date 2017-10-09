package pl.domsoft.deviceMonitor.infrastructure.device.entities.events.views;

import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceEventType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * Created by szymo on 02.05.2017.
 * Encja przechowująca informacje o awariach urządzenia
 */
@Entity
@DiscriminatorValue("DEVICE_ACCIDENT")
public class DeviceAccidentView extends DeviceEventView {


    protected DeviceAccidentView() {
        super();
    }

    @Override
    public DeviceEventType getType() {
        return DeviceEventType.deviceAccident;
    }

}
