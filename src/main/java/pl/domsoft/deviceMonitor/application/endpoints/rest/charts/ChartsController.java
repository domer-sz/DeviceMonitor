package pl.domsoft.deviceMonitor.application.endpoints.rest.charts;

import org.springframework.web.bind.annotation.*;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.charts.model.ChartDataModel;
import pl.domsoft.deviceMonitor.infrastructure.charts.ChartDataRepository;
import pl.domsoft.deviceMonitor.infrastructure.charts.LoadChartDataModel;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities.CustomLogFieldConfig;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.repositories.CustomLogFieldConfigRepository;

import java.util.List;

/**
 * Created by szymo on 07.06.2017.
 * Kontroller podający dane do wykresów
 */
@RestController
@RequestMapping(path = "/api/chart")
@CrossOrigin(origins = "*")
public class ChartsController implements ExceptionPersistable{

    private final CustomLogFieldConfigRepository customLogFieldConfigRepository;
    private final ChartDataRepository chartDataRepository;
    private final AppExceptionRepository appExceptionRepository;

    public ChartsController(CustomLogFieldConfigRepository customLogFieldConfigRepository, ChartDataRepository chartDataRepository, AppExceptionRepository appExceptionRepository) {
        this.customLogFieldConfigRepository = customLogFieldConfigRepository;
        this.chartDataRepository = chartDataRepository;
        this.appExceptionRepository = appExceptionRepository;
    }

    @GetMapping("/availableData")
    public List<CustomLogFieldConfig> getAvailableData(){
        return customLogFieldConfigRepository.findAllOrderByViewNameDesc();
    }

    @PostMapping()
    public ChartDataModel getChartData(@RequestBody LoadChartDataModel loadChartDataModel) throws AppException {
        if(loadChartDataModel.getLogFieldIds() == null || loadChartDataModel.getLogFieldIds().isEmpty()){
            throw new AppException("Musisz wybrać przynajniej jedną daną", getExcpRep());
        }
        return chartDataRepository.getDataForChart(loadChartDataModel);
    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
