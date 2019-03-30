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
        eventRegister.unregisterAll();
    }

    @Test
    public void register() {
        String action = "TEST_EVENT";
        eventRegister.register(action, TestEvent.class);

        Assert.assertTrue(eventRegister.isRegistered(action));
        Assert.assertTrue(eventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void registerWithoutAction() {
        eventRegister.register(TestEvent.class);

        Assert.assertTrue(eventRegister.isRegistered("TEST_EVENT"));
        Assert.assertTrue(eventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void registerAnnotated() {
        eventRegister.register(TestEventAnnotated.class);

        Assert.assertTrue(eventRegister.isRegistered("TEST_EVENT_ANNOTATED"));
        Assert.assertTrue(eventRegister.isRegistered(TestEventAnnotated.class));
    }

    @Test
    public void registerAnnotatedWithAction() {
        eventRegister.register(TestEventAnnotatedWithAction.class);

        Assert.assertTrue(eventRegister.isRegistered("TEST_EVENT_WITH_ACTION"));
        Assert.assertTrue(eventRegister.isRegistered(TestEventAnnotatedWithAction.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction1() {
        eventRegister.register("test_event", TestEvent.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerInvalidAction2() {
        eventRegister.register("TEST-EVENT", TestEvent.class);
    }

    @Test
    public void unregisterByAction() {
        String action = "TEST_EVENT";
        eventRegister.register(action, TestEvent.class);

        eventRegister.unregister(action);
        Assert.assertFalse(eventRegister.isRegistered(action));
        Assert.assertFalse(eventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void unregisterByClass() {
        String action = "TEST_EVENT";
        eventRegister.register(action, TestEvent.class);

        eventRegister.unregister(TestEvent.class);
        Assert.assertFalse(eventRegister.isRegistered(action));
        Assert.assertFalse(eventRegister.isRegistered(TestEvent.class));
    }

    @Test
    public void unregisterAll() {
        eventRegister.register(TestEvent.class);
        eventRegister.register(TestEventAnnotated.class);
        eventRegister.register(TestEventAnnotatedWithAction.class);
        eventRegister.unregisterAll();

        Assert.assertFalse(eventRegister.isRegistered(TestEvent.class));
        Assert.assertFalse(eventRegister.isRegistered(TestEventAnnotated.class));
        Assert.assertFalse(eventRegister.isRegistered(TestEventAnnotatedWithAction.class));
    }
}
