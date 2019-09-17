package gargoyle.l0x.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:log.properties")
@Data
public class LogConfig {
    @Value("${log.level}")
    private String logLevel;
}
