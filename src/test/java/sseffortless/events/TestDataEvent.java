package sseffortless.events;

import sseffortless.annotations.SSEvent;
import sseffortless.model.SSEPayload;

@SSEvent("TEST_DATA_EVENT")
public class TestDataEvent implements SSEPayload {

    private String data;
    public TestDataEvent(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TestDataEvent{" +
                "data='" + data + '\'' +
                '}';
    }
}
