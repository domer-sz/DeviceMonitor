package pl.domsoft.deviceMonitor.infrastructure.notifications.config.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.entities.email.EmailTarget;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums.NotificationTypeEnum;

/**
 * Created by szymo on 21.08.2017.
 */

public class EmailTargetModel extends NotificationTargetImplModel {

    public EmailTargetModel(){
        super();
    }

    @JsonCreator
    public EmailTargetModel(
           @JsonProperty("sendOnAccident") boolean sendOnAccident,
           @JsonProperty("sendOnOverview") boolean sendOnOverview,
           @JsonProperty("sendOnBreak")boolean sendOnBreak,
           @JsonProperty("target") String target
    ) {
        super(sendOnAccident, sendOnOverview, sendOnBreak, target);
    }

    public EmailTargetModel(EmailTarget target) {
        super(target.sendOnAccident(), target.sendOnOverview(), target.sendOnBreak(), target.getTarget());
    }

    @Override
    public NotificationTypeEnum getType(){
        return NotificationTypeEnum.EMAIL;
    }

}
