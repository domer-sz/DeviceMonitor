package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.base;

import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * Created by szymo on 03.06.2017.
 */
public class SpreadsheetRowModel <CellType extends SpreadsheetCell> {
    protected final String name;
    protected final List<CellType> cells;

    public SpreadsheetRowModel(String name, List<CellType> cells) {
        Validate.notNull(cells, name);
        this.name = name;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

    public List<CellType> getCells() {
        return cells;
    }

    public CellType addCell(CellType cell){
        this.cells.add(cell);
        return cell;
    }

    public List<CellType> addCell(List<CellType> cells){
        this.cells.addAll(cells);
        return cells;
    }
}
