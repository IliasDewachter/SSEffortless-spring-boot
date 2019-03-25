package sseffortless;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sseffortless.annotations.SSEvent;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventRegisterTest {

    @Autowired
    private EventRegister eventRegister;

    @Before
    public void setUp() {
        this.eventRegister.unregisterAll();
    }

    @Test
    public void register() {
        String action = "TEST_PAYLOAD";
        this.eventRegister.register(action, TestPayload.class);

        Assert.assertTrue(this.eventRegister.isRegistered(action));
        Assert.assertTrue(this.eventRegister.isRegistered(TestPayload.class));
    }

    @Test
    public void registerWithoutAction() {
        this.eventRegister.register(TestPayload.class);

        Assert.assertTrue(this.eventRegister.isRegistered("TEST_PAYLOAD"));
        Assert.assertTrue(this.eventRegister.isRegistered(TestPayload.class));
    }

    @Test
    public void registerAnnotated() {
        this.eventRegister.register(TestPayloadAnnotated.class);

        Assert.assertTrue(this.eventRegister.isRegistered("TEST_PAYLOAD_ANNOTATED"));
        Assert.assertTrue(this.eventRegister.isRegistered(TestPayloadAnnotated.class));
    }

    @Test
    public void registerAnnotatedWithAction() {
        this.eventRegister.register(TestPayloadAnnotatedWithAction.class);

        Assert.assertTrue(this.eventRegister.isRegistered("TEST_PAYLOAD_WITH_ACTION"));
        Assert.assertTrue(this.eventRegister.isRegistered(TestPayloadAnnotatedWithAction.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction1() {
        this.eventRegister.register("test_payload", TestPayload.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction2() {
        this.eventRegister.register("TEST-PAYLOAD", TestPayload.class);
    }

    @Test
    public void unregisterByAction() {
        String action = "TEST_PAYLOAD";
        this.eventRegister.register(action, TestPayload.class);

        this.eventRegister.unregister(action);
        Assert.assertFalse(this.eventRegister.isRegistered(action));
        Assert.assertFalse(this.eventRegister.isRegistered(TestPayload.class));
    }

    @Test
    public void unregisterByClass() {
        String action = "TEST_PAYLOAD";
        this.eventRegister.register(action, TestPayload.class);

        this.eventRegister.unregister(TestPayload.class);
        Assert.assertFalse(this.eventRegister.isRegistered(action));
        Assert.assertFalse(this.eventRegister.isRegistered(TestPayload.class));
    }


    /* CLASSES */

    class TestPayload implements SSEPayload {
    }

    @SSEvent
    class TestPayloadAnnotated implements SSEPayload {
    }

    @SSEvent(action = "TEST_PAYLOAD_WITH_ACTION")
    class TestPayloadAnnotatedWithAction implements SSEPayload {
    }
}
