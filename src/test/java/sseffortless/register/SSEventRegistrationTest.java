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

        Assert.assertFalse(this.ssEventRegister.isRegistered(AnnotationEvent1.class));
        Assert.assertTrue(this.ssEventRegister.isRegistered(AnnotationEvent2.class));
        Assert.assertTrue(this.ssEventRegister.isRegistered(AnnotationEvent3.class));
    }


    /* CLASSES */

    private class AnnotationEvent1 implements SSEPayload {
    }

    @SSEvent
    private class AnnotationEvent2 implements SSEPayload {
    }

    @SSEvent(action = "ANNOTATION_EVENT3")
    private class AnnotationEvent3 implements SSEPayload {
    }

}
