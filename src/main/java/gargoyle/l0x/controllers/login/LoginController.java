package gargoyle.l0x.controllers.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;
import static gargoyle.l0x.config.WebSecurityConfig.PATH_LOGIN;
import static gargoyle.l0x.config.WebSecurityConfig.PATH_LOGOUT;

@Controller
public class LoginController {
    public static final String VIEW_LOGIN = "login/login";

    @RequestMapping(PATH_LOGIN)
    public ModelAndView getLogin() {
        return new ModelAndView(VIEW_LOGIN);
    }

    @GetMapping(PATH_LOGOUT)
    public String getLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null != auth) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        for (Cookie cookie : request.getCookies()) {
            if (AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY.equals(cookie.getName())) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:" + PATH_ROOT;
    }
}
