package gargoyle.l0x.advice;

import gargoyle.l0x.config.SymbolConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class SymbolAdvice {
    private final SymbolConfig config;

    @ModelAttribute("projectName")
    public String getProjectName() {
        return config.getProjectName();
    }

    @ModelAttribute("projectDescription")
    public String getProjectDescription() {
        return config.getProjectDescription();
    }

    @ModelAttribute("projectVersion")
    public String getProjectVersion() {
        return config.getProjectVersion();
    }

    @ModelAttribute("projectBuild")
    public String getProjectBuild() {
        return config.getProjectBuild();
    }

    @ModelAttribute("projectOrganizationName")
    public String getProjectOrganizationName() {
        return config.getProjectOrganizationName();
    }

    @ModelAttribute("projectOrganizationUrl")
    public String getProjectOrganizationUrl() {
        return config.getProjectOrganizationUrl();
    }

    @ModelAttribute("projectInceptionYear")
    public String getProjectInceptionYear() {
        return config.getProjectInceptionYear();
    }

    @ModelAttribute("systemDateFormat")
    public String getSystemDateFormat() {
        return config.getSystemDateFormat();
    }

    @ModelAttribute("systemDateTimeFormat")
    public String getSystemDateTimeFormat() {
        return config.getSystemDateTimeFormat();
    }

    @ModelAttribute("systemTimestampFormat")
    public String getSystemTimestampFormat() {
        return config.getSystemTimestampFormat();
    }
}
