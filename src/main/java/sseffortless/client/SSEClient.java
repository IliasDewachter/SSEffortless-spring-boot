package sseffortless.client;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sseffortless.model.Event;

import java.io.IOException;

public abstract class SSEClient {

    private final SseEmitter sseEmitter;
    private final Gson gson;

    public SSEClient(long timeout) {
        this.sseEmitter = new SseEmitter(timeout);
        gson = new Gson();
    }

    abstract boolean prePush(Event event);

    public void push(Event event) throws IOException {
        if (!this.prePush(event)) return;
        sseEmitter.send(SseEmitter.event()
                .name(event.getAction())
                .data(gson.toJson(event.getPayload()), MediaType.APPLICATION_JSON)
                .build());
    }

    public SseEmitter getSseEmitter() {
        return sseEmitter;
    }
}
