package pl.domsoft.deviceMonitor.infrastructure.device.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.device.commands.GenerateDeviceLogsCommand;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceLog;
import pl.domsoft.deviceMonitor.infrastructure.device.handlers.interfaces.GenerateDeviceLogsHandler;
import pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog.DeviceLogRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.utils.DateTimeUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szymo on 23.04.2017.
 * Implementacja handlera komendy {@link GenerateDeviceLogsCommand}
 */
@Component
class GenerateDeviceLogsHandlerImpl implements GenerateDeviceLogsHandler {

    @Autowired
    private DeviceLogRepository deviceLogRepository;
    private final static int NUMBER_OF_ENTITY_TO_FLUSH = 300000;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GenerateDeviceLogsHandlerImpl() {
    }

    @Override
    public String handle(GenerateDeviceLogsCommand command) {
        try {
            LocalDateTime start = DateTimeUtils.dateToLocalDateTime(command.getStart());
            LocalDateTime end = DateTimeUtils.dateToLocalDateTime(command.getEnd());
            LocalDateTime iterationDate = start.plusDays(0);

            List<DeviceLog> logs = new ArrayList<>();
            DataSource ds = jdbcTemplate.getDataSource();
            Connection connection = ds.getConnection();

            connection.setAutoCommit(false);

            String sql = prepareSql();

            PreparedStatement ps = connection.prepareStatement(sql);

            while (iterationDate.isBefore(end)) {
                logs.addAll(this.generateLogsForDate(iterationDate, command));
                if (logs.size() >= NUMBER_OF_ENTITY_TO_FLUSH) {
                    logs = flushDeviceLogs(logs, ps, connection);
                    System.gc();
                }
                iterationDate = iterationDate.plusSeconds(command.getDensityInSec());
            }
            if (logs != null && !logs.isEmpty()) {
                flushDeviceLogs(logs, ps, connection);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Zadanie wykonano pomyślnie";
    }


    private List<DeviceLog> flushDeviceLogs(List<DeviceLog> logs, PreparedStatement ps, Connection connection) {
        try {
            for (DeviceLog l : logs) {

                ps.setString(1, l.getUuid());
                ps.setInt(2, l.getAnalogIn1());
                ps.setInt(3, l.getAnalogIn2());
                ps.setInt(4, l.getAnalogIn3());
                ps.setInt(5, l.getAnalogIn4());
                ps.setInt(6, l.getAnalogIn5());
                ps.setInt(7, l.getAnalogIn6());
                ps.setInt(8, l.getAnalogIn7());
                ps.setInt(9, l.getAnalogIn8());
//                ps.setInt(10, l.getAnalogIn9());
//                ps.setInt(11, l.getAnalogIn10());
//                ps.setInt(12, l.getAnalogOut1());
//                ps.setInt(13, l.getAnalogOut2());
//                ps.setInt(14, l.getAnalogOut3());
//                ps.setInt(15, l.getAnalogOut4());
//                ps.setInt(16, l.getAnalogOut5());
//                ps.setInt(17, l.getAnalogOut6());
//                ps.setInt(18, l.getAnalogOut7());
//                ps.setInt(19, l.getAnalogOut8());
//                ps.setInt(20, l.getAnalogOut9());
//                ps.setInt(21, l.getAnalogOut10());
                ps.setString(22, l.getDeviceId());
                ps.setInt(23, l.getDigitalIn1());
                ps.setInt(24, l.getDigitalIn2());
                ps.setInt(25, l.getDigitalIn3());
                ps.setInt(26, l.getDigitalIn4());
                ps.setInt(27, l.getDigitalIn5());
                ps.setInt(28, l.getDigitalIn6());
                ps.setInt(29, l.getDigitalIn7());
                ps.setInt(30, l.getDigitalIn8());
                ps.setInt(31, l.getDigitalIn9());
                ps.setInt(32, l.getDigitalIn10());
                ps.setInt(33, l.getDigitalIn11());
                ps.setInt(34, l.getDigitalIn12());
                ps.setInt(35, l.getDigitalIn13());
                ps.setInt(36, l.getDigitalIn14());
                ps.setInt(37, l.getDigitalIn15());
                ps.setInt(38, l.getDigitalIn16());
                ps.setInt(39, l.getDigitalIn17());
                ps.setInt(40, l.getDigitalIn18());
                ps.setInt(41, l.getDigitalIn19());
                ps.setInt(42, l.getDigitalIn20());
                ps.setInt(43, l.getDigitalIn21());
                ps.setInt(44, l.getDigitalIn22());
                ps.setInt(45, l.getDigitalIn23());
                ps.setInt(46, l.getDigitalIn24());
//                ps.setInt(47, l.getDigitalIn25());
                ps.setInt(48, l.getDigitalOut1());
                ps.setInt(49, l.getDigitalOut2());
                ps.setInt(50, l.getDigitalOut3());
                ps.setInt(51, l.getDigitalOut4());
                ps.setInt(52, l.getDigitalOut5());
                ps.setInt(53, l.getDigitalOut6());
                ps.setInt(54, l.getDigitalOut7());
                ps.setInt(55, l.getDigitalOut8());
                ps.setInt(56, l.getDigitalOut9());
                ps.setInt(57, l.getDigitalOut10());
                ps.setInt(58, l.getDigitalOut11());
                ps.setInt(59, l.getDigitalOut12());
                ps.setInt(60, l.getDigitalOut13());
                ps.setInt(61, l.getDigitalOut14());
                ps.setInt(62, l.getDigitalOut15());
                ps.setInt(63, l.getDigitalOut16());
                ps.setInt(64, l.getDigitalOut17());
                ps.setInt(65, l.getDigitalOut18());
                ps.setInt(66, l.getDigitalOut19());
                ps.setInt(67, l.getDigitalOut20());
                ps.setInt(68, l.getDigitalOut21());
                ps.setInt(69, l.getDigitalOut22());
                ps.setInt(70, l.getDigitalOut23());
                ps.setInt(71, l.getDigitalOut24());
//                ps.setInt(72, l.getDigitalOut25());
                ps.setObject(73, l.getLogTimestamp());
                ps.setInt(74, l.getTimeOnCount());
                ps.addBatch();

            }
            System.out.println("StartBatch");
            ps.executeLargeBatch();
            System.out.println("EndBatch");
            ps.clearBatch();
            connection.commit();
            System.out.println("EndCommit");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    private Collection<? extends DeviceLog> generateLogsForDate(LocalDateTime iterationDate, GenerateDeviceLogsCommand command) {
        return command.getDeviceIds()
                .stream()
                .map(id -> DeviceLog.buildDeviceLogWithRandomData(id, DateTimeUtils.localDateTimeToDate(iterationDate)))
                .collect(Collectors.toList());
    }

    private String prepareSql() {
        return "INSERT INTO device_log ( uuid, analog_in1, analog_in2, analog_in3, analog_in4, analog_in5, analog_in6, analog_in7, analog_in8, analog_in9, analog_in10, analog_out1, analog_out2, analog_out3, analog_out4, analog_out5, analog_out6, analog_out7, analog_out8, analog_out9, analog_out10, device_id, digital_in1, digital_in10, digital_in11, digital_in12, digital_in13, digital_in14, digital_in15, digital_in16, digital_in17, digital_in18, digital_in19, digital_in2, digital_in20, digital_in21, digital_in22, digital_in23, digital_in24, digital_in25, digital_in3, digital_in4, digital_in5, digital_in6, digital_in7, digital_in8, digital_in9, digital_out1, digital_out10, digital_out11, digital_out12, digital_out13, digital_out14, digital_out15, digital_out16, digital_out17, digital_out18, digital_out19, digital_out2, digital_out20, digital_out21, digital_out22, digital_out23, digital_out24, digital_out25, digital_out3, digital_out4, digital_out5, digital_out6, digital_out7, digital_out8, digital_out9, log_timestamp, time_on_count) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    }
}
