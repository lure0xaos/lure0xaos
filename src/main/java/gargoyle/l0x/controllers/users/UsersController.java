package gargoyle.l0x.controllers.users;

import gargoyle.l0x.services.auth.Auth;
import gargoyle.l0x.services.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;

@Controller
@RequestMapping(UsersController.PATH_USERS)
@RequiredArgsConstructor
public class UsersController {
    public static final String PATH_USERS = "/users";
    public static final String VIEW_PROFILE = "users/profile";
    public static final String MODEL_USER = "user";
    private final Auth auth;
    private final Users users;

    @GetMapping(PATH_ROOT)
    public ModelAndView getProfile() {
        return new ModelAndView(VIEW_PROFILE, MODEL_USER, auth.getLoggedUser().orElseThrow());
    }

    @GetMapping("/{username}")
    public ModelAndView getProfile(@PathVariable String username) {
        return new ModelAndView(VIEW_PROFILE, MODEL_USER, users.find(username).orElseThrow());
    }
}
