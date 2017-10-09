package pl.domsoft.deviceMonitor.infrastructure.base.model.entities;

/**
 * Created by szymo on 20.04.2017.
 * interfejs który po zaimplementowaniu pozwala identyfikowac encje
 */
public interface Identifiable {
    Long getId();
    String getUuid();
    int hashCode();
    boolean equals(Object that);
    String toString();
}
