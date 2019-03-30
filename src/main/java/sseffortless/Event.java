package sseffortless;

import lombok.Getter;

@Getter
class Event {

    private String action;
    private SSEPayload payload;

    Event(String action, SSEPayload payload) {
        this.action = action;
        this.payload = payload;
    }
}
