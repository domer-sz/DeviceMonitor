package pl.domsoft.deviceMonitor.infrastructure.charts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by szymo on 08.06.2017.
 * Json zapytania o dane do wykresu
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadChartDataModel {
    private final Date startDate;
    private final Date lastDate;
    private final String deviceId;
    private final List<Long> logFieldIds;

    //To tylko dlatego żę jackson nie chciał(nie wiem czemu) kożystać z JsonCreatora i się wyalał na deserializacji
    private LoadChartDataModel(){
         startDate = null;
         lastDate = null;
         deviceId = null;
         logFieldIds = null;
    }

    @JsonCreator
    public LoadChartDataModel(
            @JsonProperty("startDate") Date startDate,
            @JsonProperty("lastDate")  Date lastDate,
            @JsonProperty("deviceId")  String deviceId,
            @JsonProperty("logFieldIds") List<Long> logFieldIds) {
        this.startDate = startDate;
        this.lastDate  = lastDate;
        this.deviceId  = deviceId;
        this.logFieldIds = logFieldIds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public List<Long> getLogFieldIds() {
        return logFieldIds;
    }
}
