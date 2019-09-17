package gargoyle.l0x.controllers.app;

import gargoyle.l0x.annotations.alert.AlertReport;
import gargoyle.l0x.annotations.alert.AlertReportFailure;
import gargoyle.l0x.annotations.whitelist.WhitelistLocal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;

@Controller
@RequestMapping(PATH_ROOT)
@RequiredArgsConstructor
@WhitelistLocal
public class ErrController {
    @RequestMapping(PATH_ROOT + "err")
    public ModelAndView getError() {
        return throwException();
    }

    @RequestMapping(PATH_ROOT + "noerr")
    @AlertReport(failure = @AlertReportFailure(returns = "new org.springframework.web.servlet.ModelAndView('redirect:/')"))
    public ModelAndView noError() {
        return throwException();
    }

    @SuppressWarnings("ProhibitedExceptionThrown")
    private static ModelAndView throwException() {
        throw new RuntimeException("exception message", new RuntimeException("cause"));
    }
}
