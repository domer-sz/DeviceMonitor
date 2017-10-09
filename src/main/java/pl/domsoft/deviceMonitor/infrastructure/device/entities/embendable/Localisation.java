package pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by szymo on 20.04.2017.
 * Klasa embedowalna, agregująca informacje o lokalizacji urządzenia
 */
@Embeddable
public class Localisation {

    /**
     * szerokosc geograficzna pozycji urzadzenia
     */
    @Column(name = "LATITUDE")
    private String latitude = "";

    /**
     * dlugosc geograficzna pozycji urzadzenia
     */
    @Column(name = "LONGITUDE")
    private String longitude = "";

    @Column(name = "CITY")
    private String city = "";

    @Column(name = "STREET")
    private String street = "";

    @Column(name = "POST_CODE")
    private String postCode = "";

    @Column(name = "HOUSE_NUM")
    private String houseNumber = "";

    @Column(name = "APARTMENT_NUM")
    private String apartmentNumber = "";

    public Localisation(String latitude, String longitude, String city, String postCode, String street, String houseNumber, String apartmentNumber) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public Localisation(){}

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public String getPostCode() {
        return postCode;
    }

}
