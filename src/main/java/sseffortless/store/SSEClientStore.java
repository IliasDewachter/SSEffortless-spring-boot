package sseffortless.store;

import sseffortless.client.SSEClient;

import java.util.*;

public class SSEClientStore<K> {

    private final Map<K, SSEClient> clientStore;

    public SSEClientStore() {
        clientStore = new HashMap<>();
    }

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

    public Collection<SSEClient> getClients() {
        return clientStore.values();
    }

    public Collection<SSEClient> getClients(Collection<K> keys) {
        List<SSEClient> clients = new ArrayList<>();
        for (K key : keys) {
            clients.add(clientStore.get(key));
        }
        return clients;
    }

    public SSEClient getClient(K key) {
        return clientStore.get(key);
    }

    public boolean isRegistered(K key) {
        return clientStore.containsKey(key);
    }

    public boolean isRegistered(SSEClient client) {
        return clientStore.containsValue(client);
    }

    public boolean unregister(K key) {
        return clientStore.remove(key) != null;
    }

    public boolean unregister(SSEClient client) {
        for (K key : clientStore.keySet()) {
            SSEClient sseClient = clientStore.get(key);
            if (client == sseClient) {
                clientStore.remove(key);
                return true;
            }
        }
        return false;
    }

    public void unregisterAll() {
        clientStore.clear();
    }
}
