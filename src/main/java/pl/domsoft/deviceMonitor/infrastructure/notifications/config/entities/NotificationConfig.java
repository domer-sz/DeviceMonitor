package pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.domsoft.deviceMonitor.infrastructure.base.model.entities.BaseEntity;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationSendTimeModel;
import pl.domsoft.deviceMonitor.infrastructure.shared.model.converts.interfaces.Transportable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szymo on 22.08.2017.
 * Encja bazowa konfiguracji powiadomień
 */
@Entity
@Table(name = "notification_configs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "config_type", discriminatorType = DiscriminatorType.STRING)
public abstract class NotificationConfig<TDTO extends Serializable, TTarget extends BaseTarget> extends BaseEntity implements Transportable<TDTO>{

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "notificationConfig")
    private List<NotificationSendTime> notificationSendTimes = new ArrayList<>();

    public abstract List<TTarget> getTargets();

    public NotificationConfig() {
    }

    public NotificationConfig(List<NotificationSendTimeModel> notificationSendTimeModels) {
        if(this.notificationSendTimes == null) this.notificationSendTimes = new ArrayList<>();
        if(notificationSendTimeModels != null) {
            for (NotificationSendTimeModel sendTimeModel : notificationSendTimeModels) {
                this.notificationSendTimes.add(new NotificationSendTime(sendTimeModel, this));
            }
        }
    }

    public final List<NotificationSendTime> getNotificationSendTimes() {
        return notificationSendTimes;
    }

    public void setNotificationSendTimes(List<NotificationSendTime> notificationSendTimes) {
        this.notificationSendTimes = notificationSendTimes;
    }


}
