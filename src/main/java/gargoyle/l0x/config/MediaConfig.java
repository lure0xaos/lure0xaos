package gargoyle.l0x.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("media.properties")
@Data
public class MediaConfig {
    @Value("${image.location}")
    private String imageLocation;
}
