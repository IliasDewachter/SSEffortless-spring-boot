package sseffortless.clients.store;

import sseffortless.clients.client.SSEClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SSEClientStore<K> {

    private Map<K, SSEClient> clientStore = new HashMap<>();

    public void register(K key, SSEClient client) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(client);

        if (this.isRegistered(key)) {
            throw new IllegalStateException(String.format("Key %s is already registered.", key.toString()));
        }
        if (this.isRegistered(client)) {
            throw new IllegalStateException("Client is already registered.");
        }
        clientStore.put(key, client);
    }

    public boolean unregister(K key) {
        return clientStore.remove(key) != null;
    }

    public boolean isRegistered(K key) {
        return clientStore.containsKey(key);
    }

    public boolean isRegistered(SSEClient client) {
        return clientStore.containsValue(client);
    }

    public void unregisterAll() {
        clientStore.clear();
    }
}
