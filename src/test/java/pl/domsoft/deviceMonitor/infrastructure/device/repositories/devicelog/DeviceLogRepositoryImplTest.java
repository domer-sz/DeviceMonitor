package pl.domsoft.deviceMonitor.infrastructure.device.repositories.devicelog;

import org.junit.Before;
import org.junit.Test;
import pl.domsoft.deviceMonitor.infrastructure.user.editableconfig.entities.CustomLogFieldConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by szymo on 08.06.2017.
 */
public class DeviceLogRepositoryImplTest {


    private DeviceLogRepositoryImpl deviceLogRepositoryImpl;

    @Before
    public void setUp(){
        deviceLogRepositoryImpl = new DeviceLogRepositoryImpl(null);
    }

    @Test
    public void convertToSqlInList() throws Exception {
        //given
        List<String> deviceIds = new ArrayList<>(Arrays.asList("dev_1", "dev_2", "dev_3"));
        //when
        final String sqlInList = this.deviceLogRepositoryImpl.convertToSqlInList(deviceIds);
        //then
        assertEquals("('dev_1', 'dev_2', 'dev_3')", sqlInList);
    }

    @Test
    public void buildSQLSelect() throws Exception {
        //given
        List<CustomLogFieldConfig> fields = new ArrayList<>(Arrays.asList(
                new CustomLogFieldConfig("aaa", "aaa_aa"),
                new CustomLogFieldConfig("bbb", "bbb_aa"),
                new CustomLogFieldConfig("ccc", "ccc_aa"),
                new CustomLogFieldConfig("ddd", "eee_aa")
            )
        );

        //when
        final String result = deviceLogRepositoryImpl.buildSQLSelect(fields, "dl.");

        //then
        assertEquals(" dl.aaa_aa, dl.bbb_aa, dl.ccc_aa, dl.eee_aa ", result);
    }

}