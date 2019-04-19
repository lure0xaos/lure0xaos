package gargoyle.l0x.controllers.away;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.URL;

@Controller
public class AwayController {
    public static final String VIEW_AWAY = "away/away";
    public static final String MODEL_URL = "url";

    @RequestMapping("/to/")
    public ModelAndView getAway(@RequestParam("url") URL url) {
        return new ModelAndView(VIEW_AWAY,
                MODEL_URL, url
        );
    }

}
