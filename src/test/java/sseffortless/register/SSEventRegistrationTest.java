package sseffortless.register;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sseffortless.SSEPayload;
import sseffortless.annotations.SSEvent;
import sseffortless.events.TestEvent;
import sseffortless.events.TestEventAnnotated;
import sseffortless.events.TestEventAnnotatedWithAction;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SSEventRegistrationTest {

    @Autowired
    private SSEventRegistration ssEventRegistration;
    @Autowired
    private SSEventRegister ssEventRegister;

    @Before
    public void setUp() {
        this.ssEventRegister.unregisterAll();
    }

    @Test
    public void registerEventWithAnnotations() {
        this.ssEventRegistration.registerSSEventAnnotations(true);

        Assert.assertFalse(this.ssEventRegister.isRegistered(TestEvent.class));
        Assert.assertTrue(this.ssEventRegister.isRegistered(TestEventAnnotated.class));
        Assert.assertTrue(this.ssEventRegister.isRegistered(TestEventAnnotatedWithAction.class));
    }
}
