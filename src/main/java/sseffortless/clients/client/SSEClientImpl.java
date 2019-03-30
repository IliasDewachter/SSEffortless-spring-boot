package sseffortless.clients.client;

public class SSEClientImpl extends SSEClient {

    SSEClientImpl(long timeout) {
        super(timeout);
    }

    @Override
    public boolean prePush(Object data) {
        return true;
    }
}
