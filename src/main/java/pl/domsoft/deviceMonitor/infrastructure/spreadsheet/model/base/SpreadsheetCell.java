package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.base;

/**
 * Created by szymo on 03.06.2017.
 */
public class SpreadsheetCell {
    protected final String value;
    protected final SpreadsheetCellType spreadsheetCellType;

    public SpreadsheetCell(String value, SpreadsheetCellType spreadsheetCellType) {
        this.value = value;
        this.spreadsheetCellType = spreadsheetCellType;
    }

    public String getValue() {
        return value;
    }

    public SpreadsheetCellType getSpreadsheetCellType() {
        return spreadsheetCellType;
    }
}
