package sseffortless.client;

import sseffortless.model.SSEPayload;

class SSEClientImpl extends SSEClient {

    SSEClientImpl(long timeout) {
        super(timeout);
    }

    @Override
    public boolean prePush(String action, SSEPayload payload) {
        return true;
    }
}
