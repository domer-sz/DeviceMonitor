package pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.domsoft.deviceMonitor.infrastructure.charts.model.ChartDataModel;
import pl.domsoft.deviceMonitor.infrastructure.device.entities.DeviceLog;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceFullData;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceLogStateModel;
import pl.domsoft.deviceMonitor.infrastructure.device.model.DeviceState;
import pl.domsoft.deviceMonitor.infrastructure.shared.model.pairs.Pair;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities.CustomLogFieldConfig;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.repositories.CustomLogFieldConfigRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by szymo on 21.04.2017.
 * Repozytorium encji {@link DeviceLog}
 */
@Repository
class DeviceLogRepositoryImpl implements CustomDeviceLogRepo{

    private static final Logger log = LoggerFactory.getLogger(ChartDataModel.class);


    @PersistenceContext
    private EntityManager em;

    private final CustomLogFieldConfigRepository customLogFieldConfigRepository;

    public DeviceLogRepositoryImpl(CustomLogFieldConfigRepository customLogFieldConfigRepository) {
        this.customLogFieldConfigRepository = customLogFieldConfigRepository;
    }

    @Value("${device.log.warning.port}")
    private String warningPort;

    @Value("${device.log.overview.port}")
    private String overviewPort;

    @Value("${device.log.time.on.port}")
    private String deviceTimeOnPort;

    @Value("${device.log.analyzer.rate.in.milliseconds}")
    private long logRateInMs;

    @Value("${number.of.cycles.to.alert}")
    private long numberOfCyclesToAlert;

    @Override
    public Collection<DeviceLogStateModel> findLastLogs() {

        String queryStr = " SELECT t.device_id, t.log_timestamp, t."+warningPort+", t."+overviewPort+", t."+deviceTimeOnPort+
                " FROM" +
                " ( " +
                " SELECT i.device_id, MAX(i.log_timestamp) AS lastdate " +
                " FROM device_log AS i " +
                " GROUP BY i.device_id "+
                " ) "+
                " AS x inner join device_log AS t on t.device_id = x.device_id AND t.log_timestamp = x.lastdate ";

         Query query = em.createNativeQuery(queryStr);
        List<Object[]> resultTableRowList = query.getResultList();
        List<DeviceLogStateModel> resultObjectRowList = new ArrayList<>(resultTableRowList.size());
        for (Object[] row : resultTableRowList) {
            try {
                resultObjectRowList.add(
                        new DeviceLogStateModel(
                                (row[0] != null) ? (String) row[0] : null,
                                (row[1] != null) ? (Date) row[1] : null,
                                (row[2] != null) ? (int)row[2] : 0,
                                (row[3] != null) ? (int)row[3] : 0,
                                (row[4] != null) ? (int)row[4] : 0)
                );
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return resultObjectRowList;
    }

    @Override
    public Collection<DeviceLogStateModel> findLastLogs(Collection<String> deviceIds) {
        String parameterList = convertToSqlInList(deviceIds);
        String queryStr = " SELECT t.device_id, t.log_timestamp, t."+warningPort+", t."+overviewPort+", t."+deviceTimeOnPort+
                " FROM" +
                " ( " +
                " SELECT i.device_id, MAX(i.log_timestamp) AS lastdate " +
                " FROM device_log AS i " +
                " WHERE device_id IN " + parameterList +
                " GROUP BY i.device_id "+
                " ) "+
                " AS x inner join device_log AS t on t.device_id = x.device_id AND t.log_timestamp = x.lastdate ";

        Query query = em.createNativeQuery(queryStr);
        List<Object[]> resultTableRowList = query.getResultList();
        List<DeviceLogStateModel> resultObjectRowList = new ArrayList<>(resultTableRowList.size());
        for (Object[] row : resultTableRowList) {
            try {
                resultObjectRowList.add(
                        new DeviceLogStateModel(
                                (row[0] != null) ? (String) row[0] : null,
                                (row[1] != null) ? (Date) row[1] : null,
                                (row[2] != null) ? (int)row[2] : 0,
                                (row[3] != null) ? (int)row[3] : 0,
                                (row[4] != null) ? (int)row[4] : 0)
                );
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return resultObjectRowList;
    }

    String convertToSqlInList(Collection<String> deviceIds) {
        if(deviceIds == null || deviceIds.isEmpty()) return "('')";
        else{
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            int i = 0;
            for (String devId : deviceIds) {
                builder.append("'");
                builder.append(devId);
                builder.append("'");
                i++;
                if(i != deviceIds.size()){
                    builder.append(", ");
                }
            }
            builder.append(")");
            return builder.toString();
        }

    }

    @Override
    public List<DeviceState> findLastLogs(String deviceId, int statesNumber) {


        List<Object[]> lastXLogsForDevice = em.createNativeQuery("SELECT dl.device_id, dl.log_timestamp, dl."+warningPort+", dl."+overviewPort+", dl."+deviceTimeOnPort+ " FROM device_log AS dl WHERE dl.device_id = :deviceId ORDER BY dl.log_timestamp DESC")
                .setParameter("deviceId", deviceId)
                .setMaxResults(statesNumber)
                .getResultList();
        final List<DeviceState> result = lastXLogsForDevice
                .stream()
                .map(
                        row -> {
                            try {
                                return new DeviceLogStateModel(
                                        (row[0] != null) ? (String) row[0] : null,
                                        (row[1] != null) ? (Date) row[1] : null,
                                        (row[2] != null) ? (int) row[2] : 0,
                                        (row[3] != null) ? (int) row[3] : 0,
                                        (row[4] != null) ? (int) row[4] : 0);
                            }catch (Exception e){
                                e.printStackTrace();
                                return null;
                            }
                        }
                )
                .filter(Objects::nonNull)
                .map(logModel -> logModel.checkDeviceState(LocalDateTime.now(), logRateInMs, numberOfCyclesToAlert))
                .collect(Collectors.toList());

        return result;
    }
    @Override
    public ChartDataModel findLastLogs(String deviceId, Date startDate, Date endDate, List<CustomLogFieldConfig> fields){
        String fieldsStr = buildSQLSelect(fields, "dl.");
        String query = "SELECT dl.log_timestamp, "+fieldsStr+" FROM device_log AS dl WHERE dl.device_id = :deviceId AND dl.log_timestamp >= :startDate AND dl.log_timestamp <= :endDate ORDER BY dl.log_timestamp ASC";
        List<Object[]> chartData = em.createNativeQuery(query)
                .setParameter("deviceId", deviceId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return ChartDataModel.buildChartDataModel(chartData, fields);
    }

    @Override
    public DeviceFullData findLastDeviceData(String deviceId) {

        final List<CustomLogFieldConfig> allConfiguredFields =  getAllLogFields();//customLogFieldConfigRepository.findAllOrderByViewNameDesc();

        String fieldsStr = buildSQLSelect(allConfiguredFields, "dl.");
        String query = "SELECT dl.log_timestamp, "+fieldsStr+" FROM device_log AS dl WHERE dl.device_id = :deviceId ORDER BY dl.log_timestamp DESC LIMIT 1";
        List<Object[]> deviceData = em.createNativeQuery(query)
                .setParameter("deviceId", deviceId)
                .getResultList();

        final Object[] row = deviceData.stream().findAny().orElse(new Object[0]);
        if(row.length > 0) {

            List<Pair<String, Integer>> output = new ArrayList<>(row.length);
            List<Pair<String, Integer>> input = new ArrayList<>(row.length);

            Date readRowDate = getDateFromRow(row);
            if (row.length - 1 == allConfiguredFields.size()) {
                for (int j = 1; j < row.length; j++) {
                    try {
                        if (row[j] != null) {
                            separateOutputAndInput(allConfiguredFields, row, output, input, j);
                        }
                    } catch (Exception e) {
                        log.error("date in errored row: " + row[0]);
                        log.error("value in pos " + row[j] + " : " + row[j]);
                        log.error(row.toString());
                        e.printStackTrace();
                    }
                }
            } else {
                log.info("Coś nie tak z danymi wykresów, nie zgdza się długość wiersza daych z hedersami");
            }

            return new DeviceFullData(input, output, readRowDate);
        }

        return new DeviceFullData(new ArrayList<>(), new ArrayList<>(), new Date());
    }

    private List<CustomLogFieldConfig> getAllLogFields() {
       return  Arrays.asList(
                new CustomLogFieldConfig("wej.c.1", "digital_in1"),
                new CustomLogFieldConfig("wej.c.2", "digital_in2"),
                new CustomLogFieldConfig("wej.c.3", "digital_in3"),
                new CustomLogFieldConfig("wej.c.4", "digital_in4"),
                new CustomLogFieldConfig("wej.c.5", "digital_in5"),
                new CustomLogFieldConfig("wej.c.6", "digital_in6"),
                new CustomLogFieldConfig("wej.c.7", "digital_in7"),
                new CustomLogFieldConfig("wej.c.8", "digital_in8"),
                new CustomLogFieldConfig("wej.c.9", "digital_in9"),
                new CustomLogFieldConfig("wej.c.10", "digital_in10"),
                new CustomLogFieldConfig("wej.c.11", "digital_in11"),
                new CustomLogFieldConfig("wej.c.12", "digital_in12"),
                new CustomLogFieldConfig("wej.c.13", "digital_in13"),
                new CustomLogFieldConfig("wej.c.14", "digital_in14"),
                new CustomLogFieldConfig("wej.c.15", "digital_in15"),
                new CustomLogFieldConfig("wej.c.16", "digital_in16"),
                new CustomLogFieldConfig("wej.c.17", "digital_in17"),
                new CustomLogFieldConfig("wej.c.18", "digital_in18"),
                new CustomLogFieldConfig("wej.c.19", "digital_in19"),
                new CustomLogFieldConfig("wej.c.20", "digital_in20"),
                new CustomLogFieldConfig("wej.c.21", "digital_in21"),
                new CustomLogFieldConfig("wej.c.22", "digital_in22"),
                new CustomLogFieldConfig("wej.c.23", "digital_in23"),
                new CustomLogFieldConfig("wej.c.24", "digital_in24"),
                new CustomLogFieldConfig("wyj.c.1", "digital_out1"),
                new CustomLogFieldConfig("wyj.c.2", "digital_out2"),
                new CustomLogFieldConfig("wyj.c.3", "digital_out3"),
                new CustomLogFieldConfig("wyj.c.4", "digital_out4"),
                new CustomLogFieldConfig("wyj.c.5", "digital_out5"),
                new CustomLogFieldConfig("wyj.c.6", "digital_out6"),
                new CustomLogFieldConfig("wyj.c.7", "digital_out7"),
                new CustomLogFieldConfig("wyj.c.8", "digital_out8"),
                new CustomLogFieldConfig("wyj.c.9", "digital_out9"),
                new CustomLogFieldConfig("wyj.c.10", "digital_out10"),
                new CustomLogFieldConfig("wyj.c.11", "digital_out11"),
                new CustomLogFieldConfig("wyj.c.12", "digital_out12"),
                new CustomLogFieldConfig("wyj.c.13", "digital_out13"),
                new CustomLogFieldConfig("wyj.c.14", "digital_out14"),
                new CustomLogFieldConfig("wyj.c.15", "digital_out15"),
                new CustomLogFieldConfig("wyj.c.16", "digital_out16"),
                new CustomLogFieldConfig("wyj.c.17", "digital_out17"),
                new CustomLogFieldConfig("wyj.c.18", "digital_out18"),
                new CustomLogFieldConfig("wyj.c.19", "digital_out19"),
                new CustomLogFieldConfig("wyj.c.20", "digital_out20"),
                new CustomLogFieldConfig("wyj.c.21", "digital_out21"),
                new CustomLogFieldConfig("wyj.c.22", "digital_out22"),
                new CustomLogFieldConfig("wyj.c.23", "digital_out23"),
                new CustomLogFieldConfig("wyj.c.24", "digital_out24"),
                new CustomLogFieldConfig("wej.a.1", "analog_in1"),
                new CustomLogFieldConfig("wej.a.2", "analog_in2"),
                new CustomLogFieldConfig("wej.a.3", "analog_in3"),
                new CustomLogFieldConfig("wej.a.4", "analog_in4"),
                new CustomLogFieldConfig("wej.a.5", "analog_in5"),
                new CustomLogFieldConfig("wej.a.6", "analog_in6"),
                new CustomLogFieldConfig("wej.a.7", "analog_in7"),
                new CustomLogFieldConfig("wej.a.8", "analog_in8"),
                new CustomLogFieldConfig("wyj.a.1", "analog_out1"),
                new CustomLogFieldConfig("wyj.a.2", "analog_out2"),
                new CustomLogFieldConfig("wyj.a.3", "analog_out3"),
                new CustomLogFieldConfig("wyj.a.4", "analog_out4"),
                new CustomLogFieldConfig("wyj.a.5", "analog_out5"),
                new CustomLogFieldConfig("wyj.a.6", "analog_out6"),
                new CustomLogFieldConfig("wyj.a.7", "analog_out7"),
                new CustomLogFieldConfig("wyj.a.8", "analog_out8")
        );
    }

    private void separateOutputAndInput(List<CustomLogFieldConfig> allConfiguredFields, Object[] row, List<Pair<String, Integer>> output, List<Pair<String, Integer>> input, int j) {
        if(allConfiguredFields.get(j - 1).getDbName().toLowerCase().contains("out")){
            output.add(new Pair<>(allConfiguredFields.get(j - 1).getViewName(), (Integer) row[j]));
        }else if(allConfiguredFields.get(j - 1).getDbName().toLowerCase().contains("in")){
            input.add(new Pair<>(allConfiguredFields.get(j - 1).getViewName(), (Integer) row[j]));
        }
    }


    private Date getDateFromRow(Object[] row) {
        if(row.length > 0) return (Date) row[0];
        return null;
    }

    String buildSQLSelect(List<CustomLogFieldConfig> fields, String prefix) {
        StringBuilder fieldsStr = new StringBuilder().append(" ");
        for(int i = 0; i < fields.size(); i++){
            if(i == fields.size()-1){
                fieldsStr.append(prefix).append(fields.get(i).getDbName()).append(" ");
            }else{
                fieldsStr.append(prefix).append(fields.get(i).getDbName()).append(", ");
            }
        }
        return fieldsStr.toString();
    }

}
