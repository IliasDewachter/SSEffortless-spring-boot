package sseffortless;

import com.google.gson.Gson;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sseffortless.model.Event;
import sseffortless.model.SSEPayload;
import sseffortless.client.SSEClient;
import sseffortless.register.SSEventRegister;
import sseffortless.store.SSEClientStore;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class SSEffortless<K> extends SSEClientStore<K> {

    private final Gson gson;

    private final SSEventRegister eventRegister;

    SSEffortless(SSEventRegister eventRegister) {
        super();
        this.eventRegister = eventRegister;
        this.gson = new Gson();
    }

    public void broadcast(SSEPayload payload) {
        Objects.requireNonNull(payload);
        Collection<SSEClient> clients = super.getClients();
        this.push(clients, payload);
    }

    public void multicast(Collection<K> keys, SSEPayload payload) {
        Objects.requireNonNull(keys);
        Objects.requireNonNull(payload);
        Collection<SSEClient> clients = super.getClients(keys);
        this.push(clients, payload);
    }

    public void unicast(K key, SSEPayload payload) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(payload);
        SSEClient client = super.getClient(key);
        this.push(client, payload);
    }


    private void push(SSEClient client, SSEPayload payload) {
        String action = eventRegister.getAction(payload);
        this.push(client, payload, action);
    }

    private void push(Collection<SSEClient> clients, SSEPayload payload) {
        String action = eventRegister.getAction(payload);
        for (SSEClient client : clients) {
            this.push(client, payload, action);
        }
    }

    private void push(SSEClient client, SSEPayload payload, String action) {
        if (!client.prePush(action, payload)) return;

        Event event = new Event(action, gson.toJson(payload));
        SseEmitter emitter = super.getEmitter(client);

        try {
            emitter.send(event);
        } catch (IOException e) {
            super.unregister(client);
        }
    }

}
