package pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by szymo on 31.08.2017.
 * Pola kontaktowe do właścicieli urządzenia, służą do wysyłania powiadomień alarmowych
 */
@Embeddable
public class Contact {
    @Column(name = "email")
    private String email = "";
    @Column(name = "phone_number")
    private String phoneNumber = "";
    @Column(name = "send_notifications")
    private Boolean sendNotifications = false;

    public Contact() {
    }

    public Contact(String email, String phoneNumber, Boolean sendNotifications) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sendNotifications = sendNotifications;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getSendNotifications() {
        return sendNotifications;
    }

    public void setSendNotifications(Boolean sendNotifications) {
        this.sendNotifications = sendNotifications;
    }
}
