package sseffortless;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sseffortless.client.SSEClient;
import sseffortless.client.SSEClientFactory;
import sseffortless.events.TestDataEvent;
import sseffortless.register.SSEventScanner;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class SSEffortlessTest {
    @Autowired
    private SSEffortlessFactory effortlessFactory;
    @Autowired
    private SSEClientFactory clientFactory;
    @Autowired
    private SSEventScanner eventScanner;
    @Autowired
    private TestController testController;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        eventScanner.scanSSEventAnnotations(true);
    }

    @Test
    public void unicast() throws Exception {
        SSEClient client = clientFactory.createClient();
        Long key = 1234L;
        SSEffortless<Long> effortless = effortlessFactory.createEffortless();
        SseEmitter emitter = effortless.register(key, client);
        testController.setEmitter(emitter);

        String data = "This data should be sent";
        effortless.unicast(key, new TestDataEvent(data));

        mockMvc.perform(get("/")
                .contentType(MediaType.TEXT_EVENT_STREAM))
                .andExpect(status().isOk()).andDo(handler -> {
            Assert.assertTrue(handler.getResponse().getContentAsString().contains("TEST_DATA_EVENT"));
            Assert.assertTrue(handler.getResponse().getContentAsString().contains(data));
        });
    }

    @Test
    public void broadcast() throws Exception {
        SSEClient client = clientFactory.createClient();
        Long key = 1234L;
        SSEffortless<Long> effortless = effortlessFactory.createEffortless();
        SseEmitter emitter = effortless.register(key, client);
        testController.setEmitter(emitter);

        String data = "This data should be sent";
        effortless.broadcast(new TestDataEvent(data));

        mockMvc.perform(get("/")
                .contentType(MediaType.TEXT_EVENT_STREAM))
                .andExpect(status().isOk()).andDo(handler -> {
            Assert.assertTrue(handler.getResponse().getContentAsString().contains("TEST_DATA_EVENT"));
            Assert.assertTrue(handler.getResponse().getContentAsString().contains(data));
        });
    }

    @Test
    public void multicast() throws Exception {
        SSEClient client = clientFactory.createClient();
        Long key = 1234L;
        SSEffortless<Long> effortless = effortlessFactory.createEffortless();
        SseEmitter emitter = effortless.register(key, client);
        testController.setEmitter(emitter);

        String data = "This data should be sent";
        Collection<Long> keys = new ArrayList<>();
        keys.add(key);
        effortless.multicast(keys, new TestDataEvent(data));

        mockMvc.perform(get("/")
                .contentType(MediaType.TEXT_EVENT_STREAM))
                .andExpect(status().isOk()).andDo(handler -> {
            Assert.assertTrue(handler.getResponse().getContentAsString().contains("TEST_DATA_EVENT"));
            Assert.assertTrue(handler.getResponse().getContentAsString().contains(data));
        });
    }
}