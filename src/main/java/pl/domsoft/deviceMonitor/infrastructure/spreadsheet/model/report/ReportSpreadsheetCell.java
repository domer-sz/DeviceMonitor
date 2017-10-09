package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.report;

import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.base.SpreadsheetCell;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.base.SpreadsheetCellType;

/**
 * Created by szymo on 03.06.2017.
 */
public class ReportSpreadsheetCell extends SpreadsheetCell {

    ReportSpreadsheetCell(String value, SpreadsheetCellType spreadsheetCellType) {
        super(value, spreadsheetCellType);
    }
}
