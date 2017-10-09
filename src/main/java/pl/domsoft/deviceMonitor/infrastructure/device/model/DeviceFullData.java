package pl.domsoft.deviceMonitor.infrastructure.device.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.shared.model.pairs.Pair;

import java.util.Date;
import java.util.List;

/**
 * Obiekt przechowujący krotę danych urządzenia wyranych przez konfiguracje pól
 */
public class DeviceFullData {

    private final List<Pair<String, Integer>> input;
    private final List<Pair<String, Integer>> output;
    private final Date readDate;

    @JsonCreator
    public DeviceFullData(
            @JsonProperty("input") List<Pair<String, Integer>> input,
            @JsonProperty("output") List<Pair<String, Integer>> output,
            @JsonProperty("readDate") Date readDate
    ) {
        this.input = input;
        this.output = output;
        this.readDate = readDate;
    }

    public List<Pair<String, Integer>> getInput() {
        return input;
    }

    public List<Pair<String, Integer>> getOutput() {
        return output;
    }

    public Date getReadDate() {
        return readDate;
    }
}
