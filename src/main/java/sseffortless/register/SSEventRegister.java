package sseffortless.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sseffortless.SSEPayload;
import sseffortless.register.util.ActionConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class SSEventRegister {

    private final Pattern actionPattern = Pattern.compile("[A-Z_0-9]+");

    private Map<Class<? extends SSEPayload>, String> registeredEvents = new HashMap<>();

    private final ActionConverter actionConverter;

    @Autowired
    public SSEventRegister(ActionConverter actionConverter) {
        this.actionConverter = actionConverter;
    }

    public void register(Class<? extends SSEPayload> payloadClass) {
        String action = this.actionConverter.convertToAction(payloadClass);
        this.register(action, payloadClass);
    }

    public void register(String action, Class<? extends SSEPayload> payloadClass) {
        if (!this.actionPattern.matcher(action).matches()) {
            throw new IllegalArgumentException("Actions may only consist out of capital letters, numbers and underscores");
        }
        if (this.isRegistered(payloadClass)) {
            throw new IllegalStateException(String.format("Payload %s is already registered", payloadClass.getSimpleName()));
        }
        if (this.isRegistered(action)) {
            throw new IllegalStateException(String.format("Action %s is already registered", action));
        }

        this.registeredEvents.put(payloadClass, action);
    }

    public boolean isRegistered(String action) {
        return this.registeredEvents.containsValue(action);
    }

    public boolean isRegistered(Class<? extends SSEPayload> payloadClass) {
        return this.registeredEvents.containsKey(payloadClass);
    }

    public boolean unregister(Class<? extends SSEPayload> payloadClass) {
        if (this.isRegistered(payloadClass)) {
            this.registeredEvents.remove(payloadClass);
            return true;
        }
        return false;
    }

    public boolean unregister(String action) {
        if (this.isRegistered(action)) {
            for (Map.Entry<Class<? extends SSEPayload>, String> entry : this.registeredEvents.entrySet()) {
                if (entry.getValue().equals(action)) {
                    this.registeredEvents.remove(entry.getKey());
                    return true;
                }
            }
        }
        return false;
    }

    public void unregisterAll() {
        this.registeredEvents.clear();
    }
}
