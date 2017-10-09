package pl.domsoft.deviceMonitor.application.endpoints.rest.reports;

import org.springframework.web.bind.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandBus;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.commands.GenerateStateReportCommand;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.report.ReportSpreadsheetModel;


/**
 * Created by szymo on 22.04.2017.
 */
@RestController
@RequestMapping(path = "/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final CommandBus commandBus;


    public ReportController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @RequestMapping(value = "/state", method = RequestMethod.POST)
    public ReportSpreadsheetModel generateConfigs( @RequestBody GenerateStateReportCommand command) throws AppException {
        final ReportSpreadsheetModel model = commandBus.sendCommand(command, ReportSpreadsheetModel.class);
        return model;
    }


}
