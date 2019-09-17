package gargoyle.l0x.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:media.properties")
@Data
public class MediaConfig {
    @Value("${image.location}")
    private String imageLocation;
    @Value("${image.width}")
    private int imageWidth;
    @Value("${image.height}")
    private int imageHeight;
}
