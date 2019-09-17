package gargoyle.l0x.advice;

import gargoyle.l0x.services.auth.Auth;
import gargoyle.l0x.services.sessions.Sessions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class LayoutAdvice {

    private final Auth auth;
    private final Sessions sessions;

    @ModelAttribute("lang")
    public String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }

    @ModelAttribute("online")
    public long getOnline() {
        return sessions.online();
    }

    @ModelAttribute("admin")
    public boolean isAdmin() {
        return auth.hasRole("ADMIN");
    }

    @ModelAttribute("logged")
    public boolean isLogged() {
        return auth.isFullyLogged();
    }
}
