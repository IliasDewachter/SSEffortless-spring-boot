package sseffortless.model;

import lombok.Getter;
@Getter
public class Event {

    private String action;
    private SSEPayload payload;

    public Event(String action, SSEPayload payload) {
        this.action = action;
        this.payload = payload;
    }
}
