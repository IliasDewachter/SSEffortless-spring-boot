package sseffortless.register;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sseffortless.events.TestEvent;
import sseffortless.events.TestEventAnnotated;
import sseffortless.events.TestEventAnnotatedWithAction;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SSEventScannerTest {

    @Autowired
    private SSEventScanner eventScanner;
    @Autowired
    private SSEventRegister eventRegister;

    @Before
    public void setUp() {
        this.eventRegister.unregisterAll();
    }

    @Test
    public void registerEventWithAnnotations() {
        this.eventScanner.scanSSEventAnnotations(true);

        Assert.assertFalse(this.eventRegister.isRegistered(TestEvent.class));
        Assert.assertTrue(this.eventRegister.isRegistered(TestEventAnnotated.class));
        Assert.assertTrue(this.eventRegister.isRegistered(TestEventAnnotatedWithAction.class));
    }
}
