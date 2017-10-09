package pl.domsoft.deviceMonitor.infrastructure.spreadsheet.handlers.impl.GenerateStateReportHandler;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.domsoft.IntegrationBaseTests;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceConfig;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceLog;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Contact;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.embendable.Localisation;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceAccident;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceBreak;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.events.DeviceOverview;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by szymo on 13.06.2017.
 */
public class GenerateStateReportHandlerImplTest extends IntegrationBaseTests{

    @Autowired
    private GenerateStateReportHandler generateStateReportHandler;
    @Autowired
    private DeviceBreakRepository deviceBreakRepository;
    @Autowired
    private DeviceOverviewRepository deviceOverviewRepository;
    @Autowired
    private DeviceAccidentRepository deviceAccidentRepository;
    @Autowired
    private DeviceConfigRepository deviceConfigRepository;
    @Autowired
    private DeviceLogRepository deviceLogRepository;


    @Autowired
    private DeviceEventRepository deviceEventRepository;

    @Before
    public void setUp() throws Exception {
        deviceEventRepository.deleteAll();
        deviceConfigRepository.deleteAll();
        deviceAccidentRepository.deleteAll();
        deviceOverviewRepository.deleteAll();
        deviceBreakRepository.deleteAll();
    }

    @Test
    public void shouldReport4Breaks2OverviewAnd1AccidentInPeriod() throws Exception {
       //given
        GenerateStateReportCommand command = createCommand();
        //when
        prepareConfigs(command);
        prepareEventsToShouldReport4Breaks2OverviewAnd1AccidentInPeriod(command);
        //then
        final ReportSpreadsheetModel handle = generateStateReportHandler.handle(command);
        assertEquals("4", handle.getRows().get(0).getCells().get(1).getValue());
        assertEquals("2", handle.getRows().get(0).getCells().get(5).getValue());
        assertEquals("1", handle.getRows().get(0).getCells().get(3).getValue());
    }

    @Test
    public void shouldReportOneBreak14DayLongBreak() throws Exception {
        //given
        GenerateStateReportCommand command = createCommand();
        //when
        prepareConfigs(command);
        prepareEventsToShouldReportOneBreak14DayLongBreak(command);

        //then
        final ReportSpreadsheetModel handle = generateStateReportHandler.handle(command);
        assertEquals("1", handle.getRows().get(0).getCells().get(1).getValue());
        assertEquals("14D 0H 0m 0s ", handle.getRows().get(0).getCells().get(2).getValue());
    }

    private void prepareEventsToShouldReportOneBreak14DayLongBreak(GenerateStateReportCommand command) {
        final Collection<DeviceConfig> all = deviceConfigRepository.findAll();
        LocalDateTime ldt = DateTimeUtils.getCurrentLocalDateTime();
        for (DeviceConfig config : all) {
            DeviceBreak deviceBreak1 = new DeviceBreak(
                    config.getDeviceId(),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(20)),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(6)),
                    ""
            );
            deviceBreakRepository.save(deviceBreak1);
        }
    }


    private GenerateStateReportCommand createCommand() {
        GenerateStateReportCommand c = new GenerateStateReportCommand(
                DateTimeUtils.localDateTimeToDate(DateTimeUtils.getCurrentLocalDateTime().minusMonths(1)),
                DateTimeUtils.localDateTimeToDate(DateTimeUtils.getCurrentLocalDateTime()),
                Arrays.asList("dev_1")
        );
        return c;
    }

    private void prepareConfigs(GenerateStateReportCommand command) {
        List<String> deviceIds = command.getDeviceIds();
        String prefix = "dev_";
        for (int i = 0; i < deviceIds.size(); i++) {
            String devId = deviceIds.get(i);
            DeviceConfig config = new DeviceConfig(
                    prefix+(i+1),
                    "",
                    new Localisation("55", "55", "troun", "87-100", "testowa", ""+i+1, ""),
                    new Contact()
            );
            deviceConfigRepository.save(config);
            deviceLogRepository.save(DeviceLog.buildDeviceLogWithRandomData(devId, DateTimeUtils.getCurrentDateTime()));
        }
    }

    private void prepareEventsToShouldReport4Breaks2OverviewAnd1AccidentInPeriod(GenerateStateReportCommand command) {
        final Collection<DeviceConfig> all = deviceConfigRepository.findAll();
        LocalDateTime ldt = DateTimeUtils.getCurrentLocalDateTime();
        for ( DeviceConfig config : all) {
            List<DeviceBreak> breaks = new ArrayList<>();
            DeviceBreak deviceBreak1 = new DeviceBreak(
                    config.getDeviceId(),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(20)),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(19)),
                    ""
            );
            breaks.add(deviceBreak1);
            DeviceBreak deviceBreak2 = new DeviceBreak(
                    config.getDeviceId(),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(18)),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(17)),
                    ""
            );
            breaks.add(deviceBreak2);
            DeviceBreak deviceBreak3 = new DeviceBreak(
                    config.getDeviceId(),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(16)),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(15)),
                    ""
            );
            breaks.add(deviceBreak3);
            DeviceBreak deviceBreak4 = new DeviceBreak(
                    config.getDeviceId(),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(14)),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(13)),
                    ""
            );
            breaks.add(deviceBreak4);
            deviceBreakRepository.save(breaks);

            List<DeviceOverview> overviews = new ArrayList<>();
            DeviceOverview deviceOverview1 = new DeviceOverview(
                    config.getDeviceId(),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(20)),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(19)),
                    ""
            );
            overviews.add(deviceOverview1);
            DeviceOverview deviceOverview2 = new DeviceOverview(
                    config.getDeviceId(),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(18)),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(17)),
                    ""
            );
            overviews.add(deviceOverview2);
            deviceOverviewRepository.save(overviews);

            DeviceAccident deviceAccident = new DeviceAccident(
                    config.getDeviceId(),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(20)),
                    DateTimeUtils.localDateTimeToDate(ldt.minusDays(19)),
                    ""
            );
            deviceAccidentRepository.save(deviceAccident);
        }
    }


}