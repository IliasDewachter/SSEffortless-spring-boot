package sseffortless;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sseffortless.client.SSEClient;

@RestController
public class TestController {

    private SseEmitter emitter;

    @GetMapping
    public SseEmitter getClient() {
        return emitter;
    }

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;
    }
}