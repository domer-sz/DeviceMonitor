package pl.domsoft.deviceMonitor.infrastructure.charts.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by szymo on 06.07.2017.
 * Model reprezentujący pojedyńczy wpis argument i wartość wykresową
 */
class SingleChartData {
    private final Date date;
    private final int value;

    @JsonCreator
    public SingleChartData(
            @JsonProperty("date") Date date,
            @JsonProperty("value") int value
    ) {
        this.date = date;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public int getValue() {
        return value;
    }
}
