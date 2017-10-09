package pl.domsoft.deviceMonitor.infrastructure.base.model;

/**
 * Created by szymo on 23.04.2017.
 * interfejs który po zaimplementowaniu pozwala identyfikowac do jakiego urządzenia odnosi się klasa
 */
public interface IdentifiableDevice {
    String getDeviceId();
    int hashCode();
    boolean equals(Object that);
}
