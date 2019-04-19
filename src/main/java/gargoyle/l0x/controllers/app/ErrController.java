package gargoyle.l0x.controllers.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;

@Controller
@RequestMapping(PATH_ROOT)
@RequiredArgsConstructor
public class ErrController {
    //@Whitelist
    @SuppressWarnings("ProhibitedExceptionThrown")
    @RequestMapping("/err")
    public ModelAndView getError() {
        throw new RuntimeException("exception message", new RuntimeException("cause"));
    }
}
