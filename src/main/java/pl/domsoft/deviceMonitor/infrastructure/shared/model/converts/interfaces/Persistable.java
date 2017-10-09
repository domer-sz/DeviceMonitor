package pl.domsoft.deviceMonitor.infrastructure.shared.model.converts.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;

/**
 * Created by szymo on 23.08.2017.
 * Oznacza że implementujący go obiekt posiada swoją encje do zapisu w bazie
 * @param <TEntity> - Klasa encji któa perzystuje obiekt w bazie danych
 */
public interface Persistable<TEntity extends BaseEntity> {
    TEntity toEntity();
}
