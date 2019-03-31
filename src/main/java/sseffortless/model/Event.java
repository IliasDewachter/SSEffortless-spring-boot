package sseffortless.model;

import lombok.Getter;
@Getter
public class Event {

    private String action;
    private String payload;

    public Event(String action, String payload) {
        this.action = action;
        this.payload = payload;
    }
}
