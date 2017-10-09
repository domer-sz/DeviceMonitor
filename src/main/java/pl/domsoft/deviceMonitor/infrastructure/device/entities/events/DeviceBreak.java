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
 * Encja przechowująca informacje o przerwach w działaniu urządzeń
 */
@Entity
@DiscriminatorValue("device_break")
@NamedQueries(
        @NamedQuery(
                name = DeviceBreak.Q_FIND_LAST_OPEN_BREAK_FOR_DEVICE,
                query = "SELECT db FROM DeviceBreak AS db " +
                        "WHERE db.deviceId = :deviceId " +
                        "AND db.endDate = null " +
                        "ORDER BY startDate DESC"
        )
)
public class DeviceBreak extends DeviceEvent {
    private static final String BASE = "DeviceBreak.";
    public static final String Q_FIND_LAST_OPEN_BREAK_FOR_DEVICE = BASE + "findLastOpenBreakForDevice";


    protected DeviceBreak() {
        super();
    }

    public DeviceBreak(String deviceId, Date startDate, Date endDate, String comment) {
        super(deviceId, startDate, endDate, comment);
    }



    @Override
    public DeviceEventType getType() {
        return DeviceEventType.deviceBreak;
    }

    @Override
    public String toTextLine(DeviceConfig deviceConfig) {
        final Localisation loc = deviceConfig.getLocalisation();
        StringBuilder rowBuilder = new StringBuilder(
                "Przerwa działania w lokalizacji: "
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
