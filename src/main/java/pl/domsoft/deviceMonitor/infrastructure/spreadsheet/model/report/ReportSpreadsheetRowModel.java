package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.report;

import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.base.SpreadsheetCellType;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.base.SpreadsheetRowModel;

import java.util.List;

/**
 * Created by szymo on 03.06.2017.
 */
public class ReportSpreadsheetRowModel extends SpreadsheetRowModel<ReportSpreadsheetCell> {
    public ReportSpreadsheetRowModel(String name, List<ReportSpreadsheetCell> cells) {
        super(name, cells);
    }

    public ReportSpreadsheetCell addStringCell(String value){
        return this.addTypedCell(value, SpreadsheetCellType.STRING);
    }

    public ReportSpreadsheetCell addIntCell(int value){
        return this.addTypedCell(String.valueOf(value), SpreadsheetCellType.INTEGER);
    }

    private ReportSpreadsheetCell addTypedCell(String value, SpreadsheetCellType type){
        ReportSpreadsheetCell cell = new ReportSpreadsheetCell(value, type);
        this.cells.add(cell);
        return cell;
    }

}
