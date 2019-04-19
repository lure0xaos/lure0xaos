package gargoyle.l0x.controllers.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static gargoyle.l0x.config.WebSecurityConfig.PATH_LOGIN;
import static gargoyle.l0x.config.WebSecurityConfig.PATH_LOGOUT;
import static gargoyle.l0x.controllers.app.AppController.VIEW_INDEX;

@Controller
public class LoginController {
    public static final String VIEW_LOGIN = "login/login";

    @RequestMapping(PATH_LOGIN)
    public ModelAndView getLogin() {
        return new ModelAndView(VIEW_LOGIN);
    }

    @RequestMapping(PATH_LOGOUT)
    public ModelAndView getLogout() {
        return new ModelAndView(VIEW_INDEX);
    }
}
