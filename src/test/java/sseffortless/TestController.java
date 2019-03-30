package sseffortless;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sseffortless.client.SSEClient;

@RestController
public class TestController {

    private SSEClient client;

    @GetMapping
    public SseEmitter getClient() {
        return client.getSseEmitter();
    }

    public void setClient(SSEClient client) {
        this.client = client;
    }
}