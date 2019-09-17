package gargoyle.l0x.controllers.admin;

import gargoyle.l0x.services.sessions.Sessions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static gargoyle.l0x.config.AppConfig.SLASH;
import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;
import static gargoyle.l0x.config.WebSecurityConfig.PATH_ADMIN;

@Controller
@RequestMapping(PATH_ADMIN + AdminSessionsController.PATH_SESSIONS)
@RequiredArgsConstructor
public class AdminSessionsController {
    public static final String PATH_SESSIONS = PATH_ROOT + "sessions";
    public static final String VIEW_ADMIN_SESSIONS = "admin" + SLASH + "sessions";
    public static final String MODEL_SESSIONS = "sessions";
    private final Sessions sessions;

    @GetMapping(PATH_ROOT)
    public ModelAndView getIndex() {
        return new ModelAndView(VIEW_ADMIN_SESSIONS, MODEL_SESSIONS, sessions.getSessions());
    }

    @RequestMapping(PATH_ROOT + "{username}" + SLASH + "kill")
    public String kill(@PathVariable String username) {
        sessions.killByUsername(username);
        return "redirect:" + PATH_ADMIN + PATH_SESSIONS + PATH_ROOT;
    }
}
