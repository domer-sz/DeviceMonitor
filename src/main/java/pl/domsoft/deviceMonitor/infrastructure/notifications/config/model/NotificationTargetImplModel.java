package pl.domsoft.deviceMonitor.infrastructure.notifications.config.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums.NotificationTypeEnum;

/**
 * Created by szymo on 21.08.2017.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class NotificationTargetImplModel implements NotificationTargetModel {

    protected final boolean sendOnAccident;
    protected final boolean sendOnOverview;
    protected final boolean sendOnBreak;
    protected final String target;


    NotificationTargetImplModel() {
        this.sendOnAccident = false;
        this.sendOnOverview = false;
        this.sendOnBreak = false;
        this.target = "";
    }

    @JsonCreator
    NotificationTargetImplModel(
            @JsonProperty("sendOnAccident") boolean sendOnAccident,
            @JsonProperty("sendOnOverview") boolean sendOnOverview,
            @JsonProperty("sendOnBreak") boolean sendOnBreak,
            @JsonProperty("target") String target
    ) {
        this.sendOnAccident = sendOnAccident;
        this.sendOnOverview = sendOnOverview;
        this.sendOnBreak = sendOnBreak;
        this.target = target;
    }

    @Override
    public abstract NotificationTypeEnum getType();

    @Override
    public boolean sendOnAccident() {
        return sendOnAccident;
    }

    @Override
    public boolean sendOnOverview() {
        return sendOnOverview;
    }

    @Override
    public boolean sendOnBreak() {
        return sendOnBreak;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
