package sseffortless.store;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sseffortless.client.SSEClient;
import sseffortless.client.SSEClientFactory;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SSEClientStoreTest {

    private SSEClientStore<Long> clientStore = new SSEClientStore<>();

    @Autowired
    private SSEClientFactory clientFactory;

    @Test
    public void registerClient() {
        SSEClient client = clientFactory.createClient();
        long key = 1000L;

        clientStore.register(key, client);

        Assert.assertTrue(clientStore.isRegistered(key));
        Assert.assertTrue(clientStore.isRegistered(client));
    }

    @Test(expected = IllegalStateException.class)
    public void registerKeyTwice() {
        SSEClient client1 = clientFactory.createClient();
        SSEClient client2 = clientFactory.createClient();

        long key = 1000L;

        clientStore.register(key, client1);
        clientStore.register(key, client2);
    }

    @Test(expected = IllegalStateException.class)
    public void registerClientTwice() {
        SSEClient client = clientFactory.createClient();

        long key1 = 1000L;
        long key2 = 2000L;

        clientStore.register(key1, client);
        clientStore.register(key2, client);
    }

    @Test
    public void unregisterClient() {
        SSEClient client = clientFactory.createClient();
        long key = 1000L;

        clientStore.register(key, client);
        clientStore.unregister(key);

        Assert.assertFalse(clientStore.isRegistered(key));
        Assert.assertFalse(clientStore.isRegistered(client));
    }

    @Test
    public void unregisterAllClients() {
        SSEClient client1 = clientFactory.createClient();
        SSEClient client2 = clientFactory.createClient();

        long key1 = 1000L;
        long key2 = 2000L;

        clientStore.register(key1, client1);
        clientStore.register(key2, client2);
        clientStore.unregisterAll();

        Assert.assertFalse(clientStore.isRegistered(key1));
        Assert.assertFalse(clientStore.isRegistered(key2));
        Assert.assertFalse(clientStore.isRegistered(client1));
        Assert.assertFalse(clientStore.isRegistered(client2));
    }
}