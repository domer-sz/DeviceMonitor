package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.impl.GenerateStateReportHandler;

import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Localisation;

/**
 * Created by szymo on 04.06.2017.
 */
class DeviceLocalisation{
    private final Localisation localisation;
    private final String deviceId;

    DeviceLocalisation(Localisation localisation, String deviceId) {
        this.localisation = localisation;
        this.deviceId = deviceId;
    }

    public Localisation getLocalisation() {
        return localisation;
    }

    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceLocalisation that = (DeviceLocalisation) o;

        return deviceId.equals(that.deviceId);
    }

    @Override
    public int hashCode() {
        return deviceId.hashCode();
    }

    @Override
    public String toString() {
        String s = "";
        if(localisation != null){
            s = localisation.getPostCode() + " " + localisation.getCity() + " " + localisation.getStreet() + " " + localisation.getHouseNumber();
            if(localisation.getApartmentNumber() != null && ! localisation.getApartmentNumber().isEmpty())
                s+= "/"+localisation.getApartmentNumber();
        }
        return s;
    }
}
