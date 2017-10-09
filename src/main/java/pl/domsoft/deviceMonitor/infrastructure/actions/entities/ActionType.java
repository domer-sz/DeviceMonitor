package pl.domsoft.deviceMonitor.infrastructure.actions.entities;

import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by szymo on 15.06.2017.
 * Typy akcji które mogą bć wysłane do urządzeń
 */
@Entity
@Table(name = "action_type")
public class ActionType extends BaseEntity{


    /**
     * wyśiwetlana nazwa akcji
     */
    @Column(name = "name")
    private String name;
    /**
      * Zawartość będąca faktyczną definicją akcji, to co tutaj się wpiszę będzie wysłane
      * do urządzenie i urządzenie podejmie próbę ykonania taj akcji
     */
    @Column(name = "content", columnDefinition = "VARCHAR(10000)")
    private String content;

    public ActionType() {
    }

    public ActionType(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
