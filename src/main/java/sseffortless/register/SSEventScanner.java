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
public class SSEventScanner implements InitializingBean {
    private final static Logger LOGGER = LoggerFactory.getLogger(SSEventScanner.class);

    private final SSEventRegister eventRegister;

    @Autowired
    public SSEventScanner(SSEventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }


    @Override
    public void afterPropertiesSet() {
        this.scanSSEventAnnotations(false);
    }

    public void scanSSEventAnnotations(boolean removeIfRegistered) {
        Iterable<Class<?>> classes = ClassIndex.getAnnotated(SSEvent.class);
        List<Class<? extends SSEPayload>> newEvents = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if (!SSEPayload.class.isAssignableFrom(clazz)) {
                LOGGER.error("SSEvent {} does not implement SSEPayload, not registering SSEvent.", clazz.getSimpleName());
                continue;
            }
            Class<? extends SSEPayload> payloadClass = clazz.asSubclass(SSEPayload.class);

            if (removeIfRegistered) {
                this.eventRegister.unregister(payloadClass);
            }

            this.eventRegister.register(payloadClass);
            newEvents.add(payloadClass);
        }

        LOGGER.info("Registered SSEvents ({}): {}", newEvents.size(), newEvents.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")));
    }
}
