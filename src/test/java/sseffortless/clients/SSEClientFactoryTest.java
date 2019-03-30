package sseffortless.clients;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SSEClientFactoryTest {

    @Autowired
    private SSEClientFactory clientFactory;

    @Value("${sseffortless.client.timeout}")
    private long timeout;

    @Test
    public void createClient() {
        SSEClient client = clientFactory.createClient(5000L);

        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getSseEmitter());
        Assert.assertEquals(5000L, (long) client.getSseEmitter().getTimeout());
    }
    @Test
    public void createClientDefaultTimeout() {
        SSEClient client = clientFactory.createClient();

        Assert.assertNotNull(client);
        Assert.assertNotNull(client.getSseEmitter());
        Assert.assertEquals(timeout, (long) client.getSseEmitter().getTimeout());
    }
}