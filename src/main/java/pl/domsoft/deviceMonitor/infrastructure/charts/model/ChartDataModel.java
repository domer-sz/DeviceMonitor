package pl.domsoft.deviceMonitor.infrastructure.charts.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities.CustomLogFieldConfig;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by szymo on 08.06.2017.
 * Struktura danych przechowująca dane wykresowe
 */
public class ChartDataModel {
    private final List<String> dataViewHeaders;
    private final Map<Integer, List<SingleChartData>> data;

    private static final Logger log = LoggerFactory.getLogger(ChartDataModel.class);

    @JsonCreator
    private ChartDataModel(
            @JsonProperty("dataViewHeaders") List<String> dataViewHeaders,
            @JsonProperty("data")Map<Integer, List<SingleChartData>> data) {
        this.dataViewHeaders = dataViewHeaders;
        this.data = data;
    }

    public List<String> getDataViewHeaders() {
        return dataViewHeaders;
    }

    public Map<Integer, List<SingleChartData>> getData() {
        return data;
    }
    @JsonIgnore
    public static ChartDataModel buildChartDataModel( List<Object[]> data, List<CustomLogFieldConfig> fields){
        final List<String> viewHeaders = fields.stream().map(CustomLogFieldConfig::getViewName).collect(Collectors.toList());
        final Map<Integer, List<SingleChartData>> mapData = new LinkedHashMap<>();

        for (int i = 0; i < viewHeaders.size(); i++) {
            mapData.put(i, new ArrayList<>());
        }

        for ( Object[] row : data){
            if(row.length-1 == viewHeaders.size()){
                for (int j = 1; j < row.length ; j++) {
                    try {
                        if(row[j] != null){
                            mapData.get(j-1).add(new SingleChartData((Date) row[0], (Integer) row[j]));
                        }
                    }catch (Exception e){
                        log.error("date in errored row: " + row[0]);
                        log.error("value in pos " + row[j] + " : " + row[j]);
                        log.error(row.toString());
                        e.printStackTrace();
                    }
                }
            }else{
                log.info("Coś nie tak z danymi wykresów, nie zgdza się długość wiersza daych z hedersami");
            }
        }


        return new ChartDataModel(viewHeaders, mapData);
    }
}
