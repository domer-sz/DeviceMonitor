package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.impl.GenerateStateReportHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceEvent;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceLogStateModel;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceconfig.DeviceConfigRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceAccidentRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceBreakRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceEventRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.deviceevent.DeviceOverviewRepository;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog.DeviceLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.commands.GenerateStateReportCommand;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.interfaces.GenerateStateReportHandler;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.report.ReportSpreadsheetModel;
import pl.domsoft.deviceMonitor.infrastructure.spreadsheet.model.report.ReportSpreadsheetRowModel;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by szymo on 03.06.2017.
 * Genertor raportu stanu urządzeń
 */
@Component
class GenerateStateReportHandlerImpl implements GenerateStateReportHandler {

    private static final Logger log = LoggerFactory.getLogger(GenerateStateReportHandlerImpl.class);


    private final DeviceBreakRepository deviceBreakRepository;
    private final DeviceOverviewRepository deviceOverviewRepository;
    private final DeviceAccidentRepository deviceAccidentRepository;
    private final DeviceConfigRepository deviceConfigRepository;
    private final DeviceLogRepository deviceLogRepository;
    private final DeviceEventRepository deviceEventRepository;

    public GenerateStateReportHandlerImpl(DeviceBreakRepository deviceBreakRepository, DeviceOverviewRepository deviceOverviewRepository, DeviceAccidentRepository deviceAccidentRepository, DeviceConfigRepository deviceConfigRepository, DeviceLogRepository deviceLogRepository, DeviceEventRepository deviceEventRepository) {
        this.deviceBreakRepository = deviceBreakRepository;
        this.deviceOverviewRepository = deviceOverviewRepository;
        this.deviceAccidentRepository = deviceAccidentRepository;
        this.deviceConfigRepository = deviceConfigRepository;
        this.deviceLogRepository = deviceLogRepository;
        this.deviceEventRepository = deviceEventRepository;
    }

    @Override
    @Transactional
    public ReportSpreadsheetModel handle(GenerateStateReportCommand command) {
        Map<DeviceLocalisation, DeviceEventSummary> reportContent = generateReport(command);
        return buildViewModel(reportContent);
    }

    private Map<DeviceLocalisation,DeviceEventSummary> generateReport(GenerateStateReportCommand command) {
        final LinkedHashMap<DeviceLocalisation, DeviceEventSummary> reportContent = new LinkedHashMap<>();
        final Collection<DeviceLogStateModel> lastLogs = deviceLogRepository.findLastLogs(command.getDeviceIds());
        Function<DeviceEvent, EventTimeModel> mapToEventTimeRange = choiceMapperFunction(command);
        Map<String, DeviceConfig> allConfigs = getConfigs(command);
        LoadedEvents loadedEvents = new LoadedEvents(command, mapToEventTimeRange).invoke();
        generateReportModelRows(reportContent, allConfigs, loadedEvents, lastLogs);
        return reportContent;
    }


    private Function<DeviceEvent, EventTimeModel> choiceMapperFunction(GenerateStateReportCommand command) {
        return (deviceEvent) -> {
            final LocalDateTime reportStartDate;
            if (command.getStartDate() != null) reportStartDate = DateTimeUtils.dateToLocalDateTime(command.getStartDate());
            else{
               reportStartDate = LocalDateTime.of(2017,1,1,0,0);
            }

            final LocalDateTime reportEndDate;
            if (command.getEndDate() != null) reportEndDate = DateTimeUtils.dateToLocalDateTime(command.getEndDate());
            else{
                reportEndDate = LocalDateTime.now();
            }

            final LocalDateTime eventStartDate;
                   if(deviceEvent.getStartDate() != null ) {
                       eventStartDate = DateTimeUtils
                               .dateToLocalDateTime(deviceEvent.getStartDate()) ;
                   }else{
                       eventStartDate = reportStartDate;
                   }
            final LocalDateTime eventEndDate;
                   if(deviceEvent.getEndDate() != null) {
                       eventEndDate =  DateTimeUtils
                               .dateToLocalDateTime(deviceEvent.getEndDate());
                   }else {
                       eventEndDate = reportEndDate;
                   }
            final EventTimeModel eventTimeModel = new EventTimeModel(
                    (eventStartDate.isBefore(reportStartDate) ? reportStartDate : eventStartDate),
                    (eventEndDate.isAfter(reportEndDate) ? reportEndDate : eventEndDate),
                    deviceEvent.getDeviceId()
            );

            return eventTimeModel;
        };
    }


    private Map<String,DeviceConfig> getConfigs(GenerateStateReportCommand command) {
        final Collection<DeviceConfig> configs;
        if(command.getDeviceIds() == null || command.getDeviceIds().isEmpty()){
             configs = deviceConfigRepository.findAll();
        }else if(command.getDeviceIds().size() == 1){
            configs = new ArrayList<>();
            final DeviceConfig first = deviceConfigRepository.findFirstDeviceConfigByDeviceId(command.getDeviceIds().get(0));
            configs.add(first);
        }else{
            configs = new ArrayList<>();
            configs.addAll(deviceConfigRepository.findAllByDeviceIdIn(command.getDeviceIds()));
        }
        Map<String,DeviceConfig> map = new LinkedHashMap<>(configs.size());
        for (DeviceConfig config : configs) {
            map.put(config.getDeviceId(), config);
        }

        return map;
    }

    private void generateReportModelRows(
            LinkedHashMap<DeviceLocalisation,
                    DeviceEventSummary> reportContent,
            Map<String, DeviceConfig> allConfigs,
            LoadedEvents loadedEvents,
            Collection<DeviceLogStateModel> lastLogs
    ) {
        Map<String, DeviceLogStateModel> mappedLastLogs = new LinkedHashMap<>();
        for (DeviceLogStateModel stateModel: lastLogs) {
            mappedLastLogs.put(stateModel.getDeviceId(), stateModel);
        }
        for (Map.Entry<String, DeviceConfig> configEntry : allConfigs.entrySet()) {
            addLocalisationRow(
                    reportContent,
                    loadedEvents.getBreakTimeModels(),
                    loadedEvents.getOverviewTimeModels(),
                    loadedEvents.getAccidentTimeModels(),
                    configEntry,
                    mappedLastLogs
            );
        }
    }

    private void addLocalisationRow(
                    LinkedHashMap<DeviceLocalisation,
                    DeviceEventSummary> reportContent,
                    List<EventTimeModel> breakTimeModels,
                    List<EventTimeModel> overviewTimeModels,
                    List<EventTimeModel> accidentTimeModels,
                    Map.Entry<String, DeviceConfig> configEntry,
                    Map<String, DeviceLogStateModel> mappedLastLogs
    ) {
        DeviceLocalisation loc = new DeviceLocalisation(configEntry.getValue().getLocalisation(), configEntry.getKey());

        int timeCounter = 0;
        try {
             timeCounter = mappedLastLogs.get(configEntry.getKey()).getDeviceTimeOnPortValue();
        }catch (Exception e){
            log.info("brak wpisu licznika czasu działania");
        }

        DeviceEventSummary summary = new DeviceEventSummary(
                checkEventsForDevice(breakTimeModels, configEntry.getKey()),
                checkEventsTimeInSecForDevice(breakTimeModels, configEntry.getKey()),
                checkEventsForDevice(accidentTimeModels, configEntry.getKey()),
                checkEventsTimeInSecForDevice(accidentTimeModels, configEntry.getKey()),
                checkEventsForDevice(overviewTimeModels, configEntry.getKey()),
                checkEventsTimeInSecForDevice(overviewTimeModels, configEntry.getKey()),
                timeCounter
                );
        reportContent.put(loc, summary);
    }


    private Long checkEventsForDevice(List<EventTimeModel> breakTimeModels, String deviceId) {
        return (Long) (long) breakTimeModels.stream()
               .filter(eventTimeModel -> deviceId.equals(eventTimeModel.getDeviceId()))
               .collect(Collectors.toList())
               .size();
    }

    private Long checkEventsTimeInSecForDevice(List<EventTimeModel> breakTimeModels, String deviceId) {
        return breakTimeModels.stream()
                .filter(eventTimeModel -> deviceId.equals(eventTimeModel.getDeviceId()))
                .mapToLong(EventTimeModel::getTimeDiffrenceInSec)
                .sum();

    }

    private ReportSpreadsheetModel buildViewModel(Map<DeviceLocalisation, DeviceEventSummary> reportContent) {
        ReportSpreadsheetModel sheetViewModel = prepareReportSpreadsheetModel();

        for (Map.Entry<DeviceLocalisation, DeviceEventSummary> entry : reportContent.entrySet()) {
            ReportSpreadsheetRowModel row = buildSingleRow(entry);

            sheetViewModel.addRow(row);
        }

        return sheetViewModel;

    }

    private ReportSpreadsheetRowModel buildSingleRow(Map.Entry<DeviceLocalisation, DeviceEventSummary> entry) {
        ReportSpreadsheetRowModel row = new ReportSpreadsheetRowModel("", new ArrayList<>());
        row.addStringCell(entry.getKey().toString());

        row.addIntCell(entry.getValue().getBreaksNumber().intValue());
        row.addStringCell(entry.getValue().getBreaksTimeFormated());

        row.addIntCell(entry.getValue().getAccidentsNumber().intValue());
        row.addStringCell(entry.getValue().getAccidentsTimeFormated());

        row.addIntCell(entry.getValue().getOverviewNumber().intValue());
        row.addStringCell(entry.getValue().getOverviewsTimeFormated());

        row.addIntCell(entry.getValue().getTimeOnCount());
        return row;
    }


    private ReportSpreadsheetModel prepareReportSpreadsheetModel() {
        return new ReportSpreadsheetModel(
                        "Raport stanu urządzeń",
                        Arrays.asList(
                                "Lokalizacja",
                                "Przerwy",
                                "Łączny czas przerw",
                                "Awarie",
                                "Łączny czas awarii",
                                "Przeglądy",
                                "Łączy czas oczekiwania na przegląd",
                                "Łączny czas działania urządzenia"
                                ),
                        new ArrayList<>()
                    );
    }

    private class LoadedEvents {
        private GenerateStateReportCommand command;
        private Function<DeviceEvent, EventTimeModel> mapToEventTimeRange;
        private List<EventTimeModel> breakTimeModels;
        private List<EventTimeModel> overviewTimeModels;
        private List<EventTimeModel> accidentTimeModels;

        LoadedEvents(GenerateStateReportCommand command, Function<DeviceEvent, EventTimeModel> mapToEventTimeRange) {
            this.command = command;
            this.mapToEventTimeRange = mapToEventTimeRange;
        }

        List<EventTimeModel> getBreakTimeModels() {
            return breakTimeModels;
        }

        List<EventTimeModel> getOverviewTimeModels() {
            return overviewTimeModels;
        }

        List<EventTimeModel> getAccidentTimeModels() {
            return accidentTimeModels;
        }

        public LoadedEvents invoke() {
            final Stream<DeviceBreak> allInTimeRangeAndStream = deviceEventRepository.findAllInTimeRangeAndStream(command.getStartDate(), command.getEndDate());
          breakTimeModels = allInTimeRangeAndStream.map(mapToEventTimeRange).collect(Collectors.toList());
            overviewTimeModels = deviceOverviewRepository.findAllInTimeRangeAndStream(command.getStartDate(), command.getEndDate())
                    .map(mapToEventTimeRange).collect(Collectors.toList());
            accidentTimeModels = deviceAccidentRepository.findAllInTimeRangeAndStream(command.getStartDate(), command.getEndDate())
                    .map(mapToEventTimeRange).collect(Collectors.toList());
            return this;
        }
    }
}

