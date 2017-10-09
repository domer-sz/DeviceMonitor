package pl.domsoft.deviceMonitor;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import pl.domsoft.IntegrationBaseTests;

import static org.junit.Assert.assertTrue;

/**
 * Created by szymo on 22.08.2017.
 */
public class TestContextLoadedTest extends IntegrationBaseTests {

    @Value("${test.profile.on}")
    private boolean testProfileOn;

    @Test
    public void testIfContextLoadsInTestProfile() {
        assertTrue(testProfileOn == true);
    }
}
