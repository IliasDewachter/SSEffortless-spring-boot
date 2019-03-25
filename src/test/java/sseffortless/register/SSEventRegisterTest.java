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

@SpringBootTest
@RunWith(SpringRunner.class)
public class SSEventRegisterTest {

    @Autowired
    private SSEventRegister SSEventRegister;

    @Before
    public void setUp() {
        this.SSEventRegister.unregisterAll();
    }

    @Test
    public void register() {
        String action = "TEST_PAYLOAD";
        this.SSEventRegister.register(action, TestPayload.class);

        Assert.assertTrue(this.SSEventRegister.isRegistered(action));
        Assert.assertTrue(this.SSEventRegister.isRegistered(TestPayload.class));
    }

    @Test
    public void registerWithoutAction() {
        this.SSEventRegister.register(TestPayload.class);

        Assert.assertTrue(this.SSEventRegister.isRegistered("TEST_PAYLOAD"));
        Assert.assertTrue(this.SSEventRegister.isRegistered(TestPayload.class));
    }

    @Test
    public void registerAnnotated() {
        this.SSEventRegister.register(TestPayloadAnnotated.class);

        Assert.assertTrue(this.SSEventRegister.isRegistered("TEST_PAYLOAD_ANNOTATED"));
        Assert.assertTrue(this.SSEventRegister.isRegistered(TestPayloadAnnotated.class));
    }

    @Test
    public void registerAnnotatedWithAction() {
        this.SSEventRegister.register(TestPayloadAnnotatedWithAction.class);

        Assert.assertTrue(this.SSEventRegister.isRegistered("TEST_PAYLOAD_WITH_ACTION"));
        Assert.assertTrue(this.SSEventRegister.isRegistered(TestPayloadAnnotatedWithAction.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction1() {
        this.SSEventRegister.register("test_payload", TestPayload.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction2() {
        this.SSEventRegister.register("TEST-PAYLOAD", TestPayload.class);
    }

    @Test
    public void unregisterByAction() {
        String action = "TEST_PAYLOAD";
        this.SSEventRegister.register(action, TestPayload.class);

        this.SSEventRegister.unregister(action);
        Assert.assertFalse(this.SSEventRegister.isRegistered(action));
        Assert.assertFalse(this.SSEventRegister.isRegistered(TestPayload.class));
    }

    @Test
    public void unregisterByClass() {
        String action = "TEST_PAYLOAD";
        this.SSEventRegister.register(action, TestPayload.class);

        this.SSEventRegister.unregister(TestPayload.class);
        Assert.assertFalse(this.SSEventRegister.isRegistered(action));
        Assert.assertFalse(this.SSEventRegister.isRegistered(TestPayload.class));
    }

    @Test
    public void unregisterAll() {
        this.SSEventRegister.register(TestPayload.class);
        this.SSEventRegister.register(TestPayloadAnnotated.class);
        this.SSEventRegister.register(TestPayloadAnnotatedWithAction.class);
        this.SSEventRegister.unregisterAll();

        Assert.assertFalse(this.SSEventRegister.isRegistered(TestPayload.class));
        Assert.assertFalse(this.SSEventRegister.isRegistered(TestPayloadAnnotated.class));
        Assert.assertFalse(this.SSEventRegister.isRegistered(TestPayloadAnnotatedWithAction.class));
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
