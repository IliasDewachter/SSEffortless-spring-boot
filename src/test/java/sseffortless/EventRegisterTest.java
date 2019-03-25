package sseffortless;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void registerManual() {
        String action = "TEST_PAYLOAD";
        this.eventRegister.register(action, TestPayload.class);

        Assert.assertTrue(this.eventRegister.isRegistered(action));
        Assert.assertTrue(this.eventRegister.isRegistered(TestPayload.class));
    }

    @Test
    public void registerAutomatic() {
        this.eventRegister.register(TestPayload.class);

        Assert.assertTrue(this.eventRegister.isRegistered("TEST_PAYLOAD"));
        Assert.assertTrue(this.eventRegister.isRegistered(TestPayload.class));
    }

    @Test
    public void registerAnnotation() {
        // TODO: 25/03/2019 Write test
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

    class TestPayload implements SSEPayload {
    }
}
