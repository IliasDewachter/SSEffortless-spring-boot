package sseffortless.store;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sseffortless.client.SSEClient;

import java.util.*;

public class SSEClientStore<K> {

    private final Map<K, Collection<SSEClient>> clientStore;
    private final Map<SSEClient, SseEmitter> emitters;

    public SSEClientStore() {
        clientStore = new HashMap<>();
        emitters = new HashMap<>();
    }

    public SseEmitter register(K key, SSEClient client) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(client);

        if (this.isRegistered(client)) {
            throw new IllegalStateException("Client is already registered.");
        }

        SseEmitter emitter = new SseEmitter(client.getTimeout());

        Collection<SSEClient> clients = clientStore.getOrDefault(key, new ArrayList<>());
        clients.add(client);
        clientStore.put(key, clients);

        emitters.put(client, emitter);
        return emitter;
    }

    protected Collection<SSEClient> getClients() {
        Collection<SSEClient> clients = new ArrayList<>();
        for (Collection<SSEClient> sseClients : clientStore.values()) {
            clients.addAll(sseClients);
        }
        return clients;
    }

    protected Collection<SSEClient> getClients(Collection<K> keys) {
        Collection<SSEClient> clients = new ArrayList<>();
        for (K key : keys) {
            clients.addAll(clientStore.get(key));
        }
        return clients;
    }

    protected Collection<SSEClient> getClients(K key) {
        return clientStore.get(key);
    }

    protected SseEmitter getEmitter(SSEClient client) {
        return emitters.get(client);
    }

    public boolean isRegistered(K key) {
        return clientStore.containsKey(key);
    }

    public boolean isRegistered(SSEClient client) {
        for (Collection<SSEClient> clients : clientStore.values()) {
            if (clients.contains(client)) return true;
        }
        return false;
    }

    public boolean unregister(K key) {
        if (!clientStore.containsKey(key)) return false;

        Collection<SSEClient> clients = clientStore.remove(key);
        for (SSEClient client : clients) {
            emitters.remove(client);
        }
        return true;
    }

    public boolean unregister(SSEClient client) {
        for (K key : clientStore.keySet()) {
            Collection<SSEClient> clients = clientStore.remove(key);

            if (clients.contains(client)) {
                clients.remove(client);
                if (clients.size() == 0) {
                    clientStore.remove(key);
                }
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
