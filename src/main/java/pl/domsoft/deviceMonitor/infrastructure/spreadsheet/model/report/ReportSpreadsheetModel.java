package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.report;

import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.base.SpreadsheetModel;

import java.util.List;

/**
 * Created by szymo on 03.06.2017.
 */
public class ReportSpreadsheetModel extends SpreadsheetModel<ReportSpreadsheetRowModel> {
    public ReportSpreadsheetModel(String name, List<String> headers, List<ReportSpreadsheetRowModel> rows) {
        super(name, headers, rows);
    }
}
