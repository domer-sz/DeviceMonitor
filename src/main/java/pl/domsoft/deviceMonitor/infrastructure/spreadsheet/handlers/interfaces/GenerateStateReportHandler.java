package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.commands.GenerateStateReportCommand;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.report.ReportSpreadsheetModel;

/**
 * Created by szymo on 03.06.2017.
 */
public interface GenerateStateReportHandler extends CommandHandler<GenerateStateReportCommand, ReportSpreadsheetModel> {
}
