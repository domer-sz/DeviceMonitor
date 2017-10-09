package pl.domsoft.deviceMonitor.infrastructure.device.entities.events;


import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Localisation;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceEventType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by szymo on 02.05.2017.
 * Encja przechowująca informacje o awariach urządzenia
 */
@Entity
@DiscriminatorValue("device_accident")
@NamedQueries(
        @NamedQuery(
                name = DeviceAccident.Q_FIND_LAST_OPEN_ACCIDENT_FOR_DEVICE,
                query = "SELECT da FROM DeviceAccident AS da " +
                        "WHERE da.deviceId = :deviceId " +
                        "AND da.endDate = null " +
                        "ORDER BY startDate DESC"
        )
)
public class DeviceAccident extends DeviceEvent {
    private static final String BASE = "DeviceAccident.";
    public static final String Q_FIND_LAST_OPEN_ACCIDENT_FOR_DEVICE = BASE + "findLastOpenAccidentsForDevice";

    protected DeviceAccident() {
        super();
    }

    public DeviceAccident(String deviceId, Date startDate, Date endDate, String comment) {
        super(deviceId, startDate, endDate, comment);
    }

    @Override
    public DeviceEventType getType() {
        return DeviceEventType.deviceAccident;
    }

    @Override
    public String toTextLine(DeviceConfig deviceConfig) {
        final Localisation loc = deviceConfig.getLocalisation();
        StringBuilder rowBuilder = new StringBuilder(
                "Zgłoszona awaria w lokalizacji: "
                        + loc.getPostCode() + " "
                        + loc.getCity() + " "
                        + loc.getStreet() + " "
                        + loc.getHouseNumber()
        );
        if(loc.getApartmentNumber() != null && !loc.getApartmentNumber().isEmpty()){
            rowBuilder.append("/"+loc.getApartmentNumber());
        }
        rowBuilder.append("<br>");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String from = dateFormat.format(this.getStartDate());
        String to = this.getEndDate() != null ? dateFormat.format(this.getEndDate()) : "trwa";
        rowBuilder.append("w czasie: ");
        rowBuilder.append(from + " - " + to);
        rowBuilder.append("<br>");

        return rowBuilder.toString();
    }

}
