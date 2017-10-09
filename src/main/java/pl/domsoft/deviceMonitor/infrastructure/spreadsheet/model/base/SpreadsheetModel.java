package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by szymo on 03.06.2017.
 * model arkusza kalkulacyjnego
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpreadsheetModel <RowType extends SpreadsheetRowModel>{

    protected String name;
    protected List<String> headers;
    protected List<RowType> rows;

    private SpreadsheetModel(){}

    @JsonCreator
    public SpreadsheetModel(String name, List<String> headers, List<RowType> rows) {
        this.name = name;
        this.headers = new ArrayList<>(headers);
        this.rows =  new ArrayList<>(rows);
    }

    public String getName() {
        return name;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<RowType> getRows() {
        return rows;
    }

    public void addHeaders(List<String> headers){
        if(this.getHeaders() == null) this.headers = new ArrayList<>(headers);
        else{
            this.headers.addAll(headers);
        }
    }

    public void addHeader(String header){
        if(this.getHeaders() == null) this.headers = new ArrayList<>(Arrays.asList(header));
        else{
            this.headers.add(header);
        }
    }

    public void addRow(List<RowType> rows){
        if(this.getRows() == null) this.rows = new ArrayList<>(rows);
        else{
            this.headers.addAll(headers);
        }
    }

    public void addRow(RowType row){
        if(this.getRows() == null) this.rows = new ArrayList<>(Arrays.asList(row));
        else{
            this.rows.add(row);
        }
    }

}
