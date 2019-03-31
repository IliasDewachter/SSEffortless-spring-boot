package sseffortless.store;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sseffortless.client.SSEClient;

import java.util.*;

public class SSEClientStore<K> {

    private final Map<K, SSEClient> clientStore;
    private final Map<SSEClient, SseEmitter> emitters;

    public SSEClientStore() {
        clientStore = new HashMap<>();
        emitters = new HashMap<>();
    }

    public SseEmitter register(K key, SSEClient client) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(client);

        if (this.isRegistered(key)) {
            throw new IllegalStateException(String.format("Key %s is already registered.", key.toString()));
        }
        if (this.isRegistered(client)) {
            throw new IllegalStateException("Client is already registered.");
        }

        SseEmitter emitter = new SseEmitter(client.getTimeout());
        clientStore.put(key, client);
        emitters.put(client, emitter);
        return emitter;
    }

    protected Collection<SSEClient> getClients() {
        return clientStore.values();
    }

    protected Collection<SSEClient> getClients(Collection<K> keys) {
        List<SSEClient> clients = new ArrayList<>();
        for (K key : keys) {
            clients.add(clientStore.get(key));
        }
        return clients;
    }

    protected SSEClient getClient(K key) {
        return clientStore.get(key);
    }

    protected SseEmitter getEmitter(SSEClient client) {
        return emitters.get(client);
    }

    public boolean isRegistered(K key) {
        return clientStore.containsKey(key);
    }

    public boolean isRegistered(SSEClient client) {
        return clientStore.containsValue(client);
    }

    public boolean unregister(K key) {
        SSEClient client = clientStore.remove(key);
        if (client == null) return false;
        emitters.remove(client);
        return true;
    }

    public boolean unregister(SSEClient client) {
        for (K key : clientStore.keySet()) {
            SSEClient sseClient = clientStore.get(key);
            if (client == sseClient) {
                clientStore.remove(key);
                emitters.remove(client);
                return true;
            }
        }
        return false;
    }

    public void unregisterAll() {
        clientStore.clear();
    }
}
