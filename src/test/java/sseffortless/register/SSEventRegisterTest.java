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
public class SSEventRegisterTest {

    @Autowired
    private SSEventRegister SSEventRegister;

    @Before
    public void setUp() {
        this.SSEventRegister.unregisterAll();
    }

    @Test
    public void register() {
        String action = "TEST_EVENT";
        this.SSEventRegister.register(action, TestEvent.class);

        Assert.assertTrue(this.SSEventRegister.isRegistered(action));
        Assert.assertTrue(this.SSEventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void registerWithoutAction() {
        this.SSEventRegister.register(TestEvent.class);

        Assert.assertTrue(this.SSEventRegister.isRegistered("TEST_EVENT"));
        Assert.assertTrue(this.SSEventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void registerAnnotated() {
        this.SSEventRegister.register(TestEventAnnotated.class);

        Assert.assertTrue(this.SSEventRegister.isRegistered("TEST_EVENT_ANNOTATED"));
        Assert.assertTrue(this.SSEventRegister.isRegistered(TestEventAnnotated.class));
    }

    @Test
    public void registerAnnotatedWithAction() {
        this.SSEventRegister.register(TestEventAnnotatedWithAction.class);

        Assert.assertTrue(this.SSEventRegister.isRegistered("TEST_EVENT_WITH_ACTION"));
        Assert.assertTrue(this.SSEventRegister.isRegistered(TestEventAnnotatedWithAction.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction1() {
        this.SSEventRegister.register("test_event", TestEvent.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction2() {
        this.SSEventRegister.register("TEST-EVENT", TestEvent.class);
    }

    @Test
    public void unregisterByAction() {
        String action = "TEST_EVENT";
        this.SSEventRegister.register(action, TestEvent.class);

        this.SSEventRegister.unregister(action);
        Assert.assertFalse(this.SSEventRegister.isRegistered(action));
        Assert.assertFalse(this.SSEventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void unregisterByClass() {
        String action = "TEST_EVENT";
        this.SSEventRegister.register(action, TestEvent.class);

        this.SSEventRegister.unregister(TestEvent.class);
        Assert.assertFalse(this.SSEventRegister.isRegistered(action));
        Assert.assertFalse(this.SSEventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void unregisterAll() {
        this.SSEventRegister.register(TestEvent.class);
        this.SSEventRegister.register(TestEventAnnotated.class);
        this.SSEventRegister.register(TestEventAnnotatedWithAction.class);
        this.SSEventRegister.unregisterAll();

        Assert.assertFalse(this.SSEventRegister.isRegistered(TestEvent.class));
        Assert.assertFalse(this.SSEventRegister.isRegistered(TestEventAnnotated.class));
        Assert.assertFalse(this.SSEventRegister.isRegistered(TestEventAnnotatedWithAction.class));
    }
}
