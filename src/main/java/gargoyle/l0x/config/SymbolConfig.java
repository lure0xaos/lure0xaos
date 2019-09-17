package gargoyle.l0x.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:symbol.properties")
@Getter
public class SymbolConfig {
    @Value("${project.name}")
    private String projectName;
    @Value("${project.description}")
    private String projectDescription;
    @Value("${project.version}")
    private String projectVersion;
    @Value("${project.organization.name}")
    private String projectOrganizationName;
    @Value("${project.organization.url}")
    private String projectOrganizationUrl;
    @Value("${project.inceptionYear}")
    private String projectInceptionYear;
    @Value("${project.build}")
    private String projectBuild;
    @Value("${system.date.format}")
    private String systemDateFormat;
    @Value("${system.datetime.format}")
    private String systemDateTimeFormat;
    @Value("${system.timestamp.format}")
    private String systemTimestampFormat;
}
