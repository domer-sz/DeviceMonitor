package pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by szymo on 16.05.2017.
 * Encja przechowująca ustawienia uzytkownika
 */
@Table(name = "app_instance_conf")
@Entity
@Cacheable(false)
public class AppInstanceConfig extends BaseEntity{
    /**
     * Czy używać konfiguracji aplikacji czy użytkownika w przypadku
     * odczytywania danych z urzadzen
     */
    @Column(name = "use_usr_log_col_conf")
    private boolean useUserLogColumnConfig;

    public AppInstanceConfig() {}

    public boolean isUseUserLogColumnConfig() {
        return useUserLogColumnConfig;
    }

    public void setUseUserLogColumnConfig(boolean useUserLogColumnConfig) {
        this.useUserLogColumnConfig = useUserLogColumnConfig;
    }
}
