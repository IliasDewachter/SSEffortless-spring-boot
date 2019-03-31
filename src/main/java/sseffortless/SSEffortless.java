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
        Collection<SSEClient> clients = super.getClients(key);
        this.push(clients, payload);
    }

    private void push(Collection<SSEClient> clients, SSEPayload originalPayload) {

        for (SSEClient client : clients) {
            SSEPayload payload = gson.fromJson(gson.toJson(originalPayload), originalPayload.getClass());
            String action = eventRegister.getAction(payload);
            if (!client.prePush(action, payload)) continue;

            Event event = new Event(action, gson.toJson(payload));
            SseEmitter emitter = super.getEmitter(client);

            try {
                emitter.send(event);
            } catch (IOException e) {
                super.unregister(client);
            }

        }
    }

}
