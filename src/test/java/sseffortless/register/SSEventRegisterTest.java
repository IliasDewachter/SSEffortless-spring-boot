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
public class SSEventRegisterTest {

    @Autowired
    private SSEventRegister eventRegister;

    @Before
    public void setUp() {
        this.eventRegister.unregisterAll();
    }

    @Test
    public void register() {
        String action = "TEST_EVENT";
        this.eventRegister.register(action, TestEvent.class);

        Assert.assertTrue(this.eventRegister.isRegistered(action));
        Assert.assertTrue(this.eventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void registerWithoutAction() {
        this.eventRegister.register(TestEvent.class);

        Assert.assertTrue(this.eventRegister.isRegistered("TEST_EVENT"));
        Assert.assertTrue(this.eventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void registerAnnotated() {
        this.eventRegister.register(TestEventAnnotated.class);

        Assert.assertTrue(this.eventRegister.isRegistered("TEST_EVENT_ANNOTATED"));
        Assert.assertTrue(this.eventRegister.isRegistered(TestEventAnnotated.class));
    }

    @Test
    public void registerAnnotatedWithAction() {
        this.eventRegister.register(TestEventAnnotatedWithAction.class);

        Assert.assertTrue(this.eventRegister.isRegistered("TEST_EVENT_WITH_ACTION"));
        Assert.assertTrue(this.eventRegister.isRegistered(TestEventAnnotatedWithAction.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction1() {
        this.eventRegister.register("test_event", TestEvent.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction2() {
        this.eventRegister.register("TEST-EVENT", TestEvent.class);
    }

    @Test
    public void unregisterByAction() {
        String action = "TEST_EVENT";
        this.eventRegister.register(action, TestEvent.class);

        this.eventRegister.unregister(action);
        Assert.assertFalse(this.eventRegister.isRegistered(action));
        Assert.assertFalse(this.eventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void unregisterByClass() {
        String action = "TEST_EVENT";
        this.eventRegister.register(action, TestEvent.class);

        this.eventRegister.unregister(TestEvent.class);
        Assert.assertFalse(this.eventRegister.isRegistered(action));
        Assert.assertFalse(this.eventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void unregisterAll() {
        this.eventRegister.register(TestEvent.class);
        this.eventRegister.register(TestEventAnnotated.class);
        this.eventRegister.register(TestEventAnnotatedWithAction.class);
        this.eventRegister.unregisterAll();

        Assert.assertFalse(this.eventRegister.isRegistered(TestEvent.class));
        Assert.assertFalse(this.eventRegister.isRegistered(TestEventAnnotated.class));
        Assert.assertFalse(this.eventRegister.isRegistered(TestEventAnnotatedWithAction.class));
    }
}
