package gargoyle.l0x.controllers.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static gargoyle.l0x.config.WebSecurityConfig.PATH_LOGIN;
import static gargoyle.l0x.config.WebSecurityConfig.PATH_LOGOUT;
import static gargoyle.l0x.controllers.app.AppController.VIEW_INDEX;

@Controller
@RequiredArgsConstructor
public class LoginController {
    public static final String VIEW_LOGIN = "login/login";
    public static final String MODEL_OAUTH_LINKS = "oauthLinks";
    public static final String AUTHORIZATION_REQUEST_BASE_URI = "oauth2/authorization";

    private final ClientRegistrationRepository clientRegistrationRepository;

    @RequestMapping(PATH_LOGIN)
    public ModelAndView getLogin() {
        Map<String, String> oauthLinks = new HashMap<>();
        ((Iterable<ClientRegistration>) clientRegistrationRepository).forEach((clientRegistration) ->
                oauthLinks.put(clientRegistration.getClientName(),
                        AUTHORIZATION_REQUEST_BASE_URI + '/' + clientRegistration.getRegistrationId()));
        return new ModelAndView(VIEW_LOGIN, MODEL_OAUTH_LINKS, oauthLinks);
    }

    @RequestMapping(PATH_LOGOUT)
    public ModelAndView getLogout() {
        return new ModelAndView(VIEW_INDEX);
    }
}
