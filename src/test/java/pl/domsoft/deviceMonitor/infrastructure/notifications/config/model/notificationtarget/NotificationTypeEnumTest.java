package pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.notificationtarget;

import org.junit.Test;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationTargetModel;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.enums.NotificationTypeEnum;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by szymo on 21.08.2017.
 */
public class NotificationTypeEnumTest {

    @Test
    public void test_if_getTargetEmptyDtos_method_restur_object_for_all_enum_vlues(){
        //given
        //when
        final List<NotificationTargetModel> targetEmptyDtos = NotificationTypeEnum.getTargetEmptyDtos();
        //then
        assertEquals(NotificationTypeEnum.values().length, targetEmptyDtos.size());
    }
}