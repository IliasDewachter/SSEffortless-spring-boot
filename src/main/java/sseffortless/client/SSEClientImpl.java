package sseffortless.client;

import sseffortless.model.Event;

class SSEClientImpl extends SSEClient {

    SSEClientImpl(long timeout) {
        super(timeout);
    }

    @Override
    boolean prePush(Event event) {
        return true;
    }
}
