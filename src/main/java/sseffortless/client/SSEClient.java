package sseffortless.client;

import sseffortless.model.SSEPayload;

public abstract class SSEClient {

    private final long timeout;
    public SSEClient(long timeout) {
        this.timeout = timeout;
    }

    public abstract boolean prePush(String action, SSEPayload payload);

    public long getTimeout() {
        return timeout;
    }
}
