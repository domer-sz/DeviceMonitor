package pl.domsoft.deviceMonitor.appconfig;


/**
 * Created by szymo on 30.04.2017.
 * STATYCZNA KLASA TRZYMAJĄCA ZMIENNE SYSTEMOWE KTÓRE MUSZĄ BY STATYCZNE I FINALNE(czylie nie można brać ich z application.properties)
 */
public class StaticFinalConfigProvider {


    /**
     * Nazwa kolejki obsługującej publikowanie stanów urządzen
     */
    public static final String JMS_DEST_DEVICE_STATE_PUBLISHER = "devicestatepublisher";

    public static final String JMS_CONTAINER_FACTORY = "JMSContainerFactory";
    /**
     * SZTUCZNA OBECNA DATA, UŻYWANA PRZY SYMULOWANYCH DANYCH
     */
    public static final String FAKE_CURRENT_DATE = "2016-02-04 17:20";
    /**
     * CZY UŻYWAC SZTUCZNEJ OBECNEJ DATY
     */
    public static final boolean USE_FAKE_DATE = false;



}
