package sseffortless.clients.client;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public abstract class SSEClient {

    private final SseEmitter sseEmitter;
    public SSEClient(long timeout) {
        this.sseEmitter = new SseEmitter(timeout);
    }

    public abstract boolean prePush(Object data);

    SseEmitter getSseEmitter() {
        return sseEmitter;
    }
}
