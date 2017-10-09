package pl.domsoft.deviceMonitor.application.endpoints.rest.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pl.domsoft.deviceMonitor.infrastructure.notifications.config.model.NotificationTargetModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by szymo on 21.08.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseNotificationsConfigCommandControllerTest {

    @Autowired
    NotificationsConfigController notificationsConfigController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_if_all_notification_target_type_has_own_empty_dto_method() throws IOException {
        //given
        List<NotificationTargetModel> targets = getNotificationTargets();

       //when
        final boolean areCreated = checkIfTheyAreCreatedEndpointOfEmptyDtosForAllTargetTypes(targets);

        //then
        assertTrue(areCreated);
    }


    private List<NotificationTargetModel> getNotificationTargets() throws IOException {
        String body = this.restTemplate.getForObject("/api/notifications/types", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        NotificationTargetModel[] types = objectMapper.readValue(body, NotificationTargetModel[].class);
        return new ArrayList<>(Arrays.asList(types));
    }

    private boolean checkIfTheyAreCreatedEndpointOfEmptyDtosForAllTargetTypes(List<NotificationTargetModel> targets){
        for (NotificationTargetModel targetType:  targets) {
            String result = this.restTemplate.getForObject("/api/notifications/empty/"+targetType.getType().toString().toLowerCase(), String.class);
            if(result.contains("404") && result.contains("Not Found")) return false;
        }
        return true;
    }
}