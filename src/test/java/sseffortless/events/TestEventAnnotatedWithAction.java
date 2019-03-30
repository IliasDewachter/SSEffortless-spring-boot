package sseffortless.events;

import sseffortless.model.SSEPayload;
import sseffortless.annotations.SSEvent;

@SSEvent("TEST_EVENT_WITH_ACTION")
public class TestEventAnnotatedWithAction implements SSEPayload {
}
