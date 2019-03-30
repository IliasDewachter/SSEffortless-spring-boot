package sseffortless.clients.client;

import org.springframework.stereotype.Component;
import sseffortless.clients.SSEClientProperties;

@Component
public class SSEClientFactory {

    private final SSEClientProperties clientProperties;

    public SSEClientFactory(SSEClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    public SSEClient createClient() {
        long timeout = clientProperties.getTimeout();
        return this.createClient(timeout);
    }

    public SSEClient createClient(long timeout) {
        return new SSEClientImpl(timeout);
    }
}
