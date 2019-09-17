package gargoyle.l0x.controllers.away;

import gargoyle.l0x.services.away.AwayConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.URL;

@Controller
public class AwayController {
    public static final String VIEW_AWAY = "away/away";
    public static final String MODEL_URL = "url";

    @RequestMapping(AwayConstants.LINK_PATH)
    public ModelAndView getAway(@RequestParam(AwayConstants.PARAM_URL) URL url) {
        return new ModelAndView(VIEW_AWAY,
                MODEL_URL, url
        );
    }

}
