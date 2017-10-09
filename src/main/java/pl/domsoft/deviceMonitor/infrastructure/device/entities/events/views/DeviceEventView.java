package pl.domsoft.deviceMonitor.infrastructure.device.entities.events.views;


import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceEventType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by szymo on 02.05.2017.
 * Encja widoku przechowująca informacje o zdarzeniach w działaniu urządzeń
 */
/*
sql budujący ten widok
CREATE VIEW dm_prod_test.events_view AS
    SELECT
        e.id AS id,
        e.device_id AS device_id,
        e.event_type AS event_type,
        e.start_date AS start_date,
        e.end_date AS end_date,
        c.latitude AS latitude,
        c.longitude AS longitude,
        UNIX_TIMESTAMP(IFNULL(e.end_date, NOW())) - UNIX_TIMESTAMP(e.start_date) AS formated_period,
		CONCAT(c.city, " ", c.street, " ", c.house_num, "\\", c.apartment_num) AS localisation
    FROM
        (dm_prod_test.device_events e
        LEFT JOIN dm_prod_test.device_configs c ON ((c.device_id = e.device_id)))
 */
@Entity
@Table(name = "events_view")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "event_type", discriminatorType = DiscriminatorType.STRING)
public abstract class DeviceEventView{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * Id urządzenia którego dotyczy zdarzenie
     */
    @Column(name = "device_id")
    protected String deviceId;

    /**
     * Początek zdarzenia
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    protected Date startDate;

    /**
     * Koniec zdarzenia
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    protected Date endDate;

    /**
     * zagniezdzony obiekt lokalizacji
     */
    @Column(name = "localisation")
    private String localisation;

    @Column(name = "formated_period")
    private String formatedPeriod;


    protected DeviceEventView() {
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public abstract DeviceEventType getType();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormatedPeriod() {
        return formatedPeriod;
    }

    public void setFormatedPeriod(String formatedPeriod) {
        this.formatedPeriod = formatedPeriod;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
}
