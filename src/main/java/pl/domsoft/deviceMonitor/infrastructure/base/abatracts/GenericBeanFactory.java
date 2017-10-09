package pl.domsoft.deviceMonitor.infrastructure.base.abatracts;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Created by szymo on 30.04.2017.
 * Generyczna fabryka beanów, rozwiązuje DI dla obiektó nie zarządzalnych
 */

public class GenericBeanFactory<T> {

    private final AutowireCapableBeanFactory autowireCapableBeanFactory;

    public GenericBeanFactory(AutowireCapableBeanFactory autowireCapableBeanFactory) {
        this.autowireCapableBeanFactory = autowireCapableBeanFactory;
    }

    /**
     * Po przekazaniu obiektu nie zarządzalnego rozwiązuje jeszo wszystkie zależności
     * któe powinny być wstrzykniętę przez kontener DI
     * @param toAutowire
     * @return
     */
    protected T autowireBean(T toAutowire){
        autowireCapableBeanFactory.autowireBean(toAutowire);
        return toAutowire;
    }
}
