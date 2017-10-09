package pl.domsoft.deviceMonitor.infrastructure.base.model.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by szymo on 20.04.2017.
 * Bazowa encja implementująca metody hashCode i equals
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable, Identifiable{

    /**
     * Klucz główny tabeli
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * pole UUID będące unikalnym kluczem encji, pozwala na poprawne identyfikowanie encji
     */

    private String uuid = UUID.randomUUID().toString();

    public Long getId() {
        return id;
    }
    public String getUuid()
    {
        return uuid;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid);
    }

    @Override
    public boolean equals(Object that)
    {   if(this.uuid == null || ((BaseEntity) that).uuid == null) {
        return false;
    }
        return this == that || that instanceof BaseEntity
                && Objects.equals(uuid, ((BaseEntity) that).uuid);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", className='"+this.getClass().getName() + "\' }";
    }
}
