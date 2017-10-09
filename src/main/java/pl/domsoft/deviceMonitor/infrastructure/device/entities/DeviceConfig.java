package pl.domsoft.deviceMonitor.infrastructure.device.entities;





import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Contact;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Localisation;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by szymo on 20.04.2017.
 * Encja przechowująca konfigurację urządzanie ktore będzie wysyłało wiadomości do aplikacji
 */
@Entity
@Table(name = "device_configs")
public class DeviceConfig extends BaseEntity {

    /**
     * Id urządzenia którego konfiguracja jest przechowywan w tej encji
     */
    @Column(name = "DEVICE_ID", unique = true)
    private String deviceId;

    /**
     * pole pozwalajace okreslic w jaki sposob odczytywac dane w urzadzniu (po stronie urzadzenia)
     */
    @Column(name = "DATA_READING_CONF")
    private String dataReadingConfiguration;

    /**
     * zagniezdzony obiekt lokalizacji
     */
    @Embedded
    private Localisation localisation;

    @Embedded
    private Contact contact;


    protected DeviceConfig() {
    }

    public DeviceConfig(String deviceId, String dataReadingConfiguration, Localisation localisation, Contact contact) {
        this.deviceId = deviceId;
        this.dataReadingConfiguration = dataReadingConfiguration;
        this.localisation = localisation;
        this.contact = contact;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDataReadingConfiguration() {
        return dataReadingConfiguration;
    }

    public void setDataReadingConfiguration(String dataReadingConfiguration) {
        this.dataReadingConfiguration = dataReadingConfiguration;
    }

    public Localisation getLocalisation() {
        if(localisation != null){
            return localisation;
        }else{
            return new Localisation();
        }
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
