package sseffortless.events;

import sseffortless.SSEPayload;
import sseffortless.annotations.SSEvent;

@SSEvent(action = "TEST_EVENT_WITH_ACTION")
public class TestEventAnnotatedWithAction implements SSEPayload {
}
