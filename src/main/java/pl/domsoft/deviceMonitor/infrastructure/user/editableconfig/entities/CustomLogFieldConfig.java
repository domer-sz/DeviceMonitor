package pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by szymo on 16.05.2017.
 * Encja przechowuję informacje o kofiguracji znaczenia pol w
 * logach urządzen {@link pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceLog}
 * wprowadzonej przez uzytkownika
 * Taka para informuje np że w kolumnie analog5 znajduje się informacja o ph wody
 */
@Entity
@Table(name = "cstm_log_field_conf")
public class CustomLogFieldConfig extends BaseEntity {

    /**
     * pole mówiące co faktycznie znajduje się na danej, bedzie to też wyświeltana nazwa
     * (np pH)
     */
    @Column(name = "view_name")
    private String viewName;
    /**
     * pole mówiące w jakiej kolumnie znajduje się ta dana
     * (np analog1)
     */
    @Column(name = "db_name")
    private String dbName;

    private CustomLogFieldConfig() {}

    public CustomLogFieldConfig(String viewName, String dbName) {
        this.viewName = viewName;
        this.dbName = dbName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
