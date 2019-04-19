package gargoyle.l0x.advice;

import gargoyle.l0x.services.sessions.Sessions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class LayoutAdvice {

    private final Sessions sessions;

    @ModelAttribute("lang")
    public String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }

    @ModelAttribute("online")
    public long getOnline() {
        return sessions.online();
    }
}
