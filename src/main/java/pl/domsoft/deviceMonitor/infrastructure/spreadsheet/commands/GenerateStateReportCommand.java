package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.interfaces.GenerateStateReportHandler;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateStateReportCommand implements Command{
    private final Date startDate;
    private final Date endDate;
    private final List<String> deviceIds;

    @JsonCreator
    public GenerateStateReportCommand(
          @JsonProperty("startDate") Date startDate,
          @JsonProperty("endDate")  Date endDate,
          @JsonProperty("deviceIds")  List<String> deviceIds
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.deviceIds = deviceIds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return GenerateStateReportHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
