package gargoyle.l0x.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;
import static gargoyle.l0x.config.WebSecurityConfig.PATH_ADMIN;

@Controller
@RequestMapping(PATH_ADMIN)
@RequiredArgsConstructor
public class AdminController {
    public static final String VIEW_ADMIN_INDEX = "admin/index";
    public static final String MODEL_ITEMS = "items";
    public static final String MODEL_ITEM = "item";

    @RequestMapping(PATH_ROOT)
    public ModelAndView getIndex() {
        return new ModelAndView(VIEW_ADMIN_INDEX);
    }
}
