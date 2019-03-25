package sseffortless.register;

import org.atteo.classindex.ClassIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sseffortless.SSEPayload;
import sseffortless.annotations.SSEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SSEventRegistration implements InitializingBean {
    private final static Logger LOGGER = LoggerFactory.getLogger(SSEventRegistration.class);

    private final SSEventRegister ssEventRegister;

    @Autowired
    public SSEventRegistration(SSEventRegister ssEventRegister) {
        this.ssEventRegister = ssEventRegister;
    }


    @Override
    public void afterPropertiesSet() {
        this.registerSSEventAnnotations();
    }

    public void registerSSEventAnnotations() {
        this.registerSSEventAnnotations(false);
    }

    public void registerSSEventAnnotations(boolean removeIfRegistered) {
        Iterable<Class<?>> classes = ClassIndex.getAnnotated(SSEvent.class);
        List<Class<? extends SSEPayload>> registeredEvents = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if (!SSEPayload.class.isAssignableFrom(clazz)) {
                LOGGER.error("SSEvent {} does not implement SSEPayload, not registering SSEvent.", clazz.getSimpleName());
                continue;
            }
            Class<? extends SSEPayload> payloadClass = clazz.asSubclass(SSEPayload.class);

            if (removeIfRegistered) {
                this.ssEventRegister.unregister(payloadClass);
            }

            this.ssEventRegister.register(payloadClass);
            registeredEvents.add(payloadClass);
        }

        LOGGER.info("Registered SSEvents: {}", registeredEvents.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")));
    }
}
