package pl.domsoft.deviceMonitor.infrastructure.shared.model.converts.interfaces;

import java.io.Serializable;

/**
 * Created by szymo on 22.08.2017.
 * Interfejs wymuszający implementującemu go obiektowi przygotowanie metody zwracającej go w postaci obiektu transportowego
 * @param <TModel> - klasa obiektu na którą bedzie zserializowany obiekt
 */
public interface Transportable<TModel extends Serializable> {
   /**
    *
    * @return zwraca wersje transportową obiektu ją wywołującą
    */
   TModel toModel();
}
