package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by szymo on 03.06.2017.
 * Enum określający typ danych znajdujących się w komórcę
 */
@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum SpreadsheetCellType {
    STRING(0L, "String"),
    INTEGER(1L, "Integer"),
    DOUBLE(2L, "Double"),
    DATE(3L, "Date");

    private Long value;
    private String label;

    SpreadsheetCellType(Long value, String label) {
        this.value = value;
        this.label = label;
    }
    public Long getValue() {
        return this.value;
    }
    public String getLabel(){
        return this.label;
    }

    @JsonCreator
    public static SpreadsheetCellType forValue(Long value) {
        switch (value.intValue()) {
            case 0:
                return SpreadsheetCellType.STRING;
            case 1:
                return SpreadsheetCellType.INTEGER;
            case 2:
                return SpreadsheetCellType.DOUBLE;
            case 3:
                return SpreadsheetCellType.DATE;
            default:
                return SpreadsheetCellType.STRING;
        }
    }

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}

