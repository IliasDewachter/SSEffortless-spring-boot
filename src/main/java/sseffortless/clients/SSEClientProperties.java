package sseffortless.clients;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sseffortless.client")
public class SSEClientProperties {
    private long timeout = 86400000L;
}
