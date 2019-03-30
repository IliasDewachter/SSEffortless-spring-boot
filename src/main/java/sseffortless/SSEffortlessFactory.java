package sseffortless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sseffortless.register.SSEventRegister;

@Component
public class SSEffortlessFactory {
    private final SSEventRegister eventRegister;

    @Autowired
    public SSEffortlessFactory(SSEventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }

    public <K> SSEffortless<K> createEffortless() {
        return new SSEffortless<>(eventRegister);
    }
}
