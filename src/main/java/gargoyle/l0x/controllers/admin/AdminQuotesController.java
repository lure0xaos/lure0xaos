package gargoyle.l0x.controllers.admin;

import gargoyle.l0x.config.WebSecurityConfig;
import gargoyle.l0x.model.app.QuoteModel;
import gargoyle.l0x.err.ItemNotFoundException;
import gargoyle.l0x.services.app.admin.quote.QuoteAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static gargoyle.l0x.config.AppConfig.SLASH;
import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;
import static gargoyle.l0x.controllers.admin.AdminController.MODEL_ITEM;
import static gargoyle.l0x.controllers.admin.AdminController.MODEL_ITEMS;

@Controller
@RequestMapping(WebSecurityConfig.PATH_ADMIN + AdminQuotesController.PATH_QUOTES)
@RequiredArgsConstructor
public class AdminQuotesController {
    public static final String PATH_QUOTES = PATH_ROOT + "quotes";
    public static final String PATH_QUOTES_EDIT = PATH_ROOT + "edit";
    public static final String VIEW_ADMIN_QUOTES = "admin" + SLASH + "quotes" + SLASH + "index";
    public static final String VIEW_ADMIN_QUOTES_EDIT = "admin" + SLASH + "quotes" + SLASH + "edit";
    private final QuoteAdminService service;

    @GetMapping(PATH_ROOT)
    public ModelAndView getIndex() {
        return new ModelAndView(VIEW_ADMIN_QUOTES, MODEL_ITEMS, service.findAll(service.getDefaultSort()));
    }

    @GetMapping(PATH_QUOTES_EDIT + PATH_ROOT)
    public ModelAndView getCreate() {
        return new ModelAndView(VIEW_ADMIN_QUOTES_EDIT, MODEL_ITEM, service.createModel());
    }

    @GetMapping(PATH_QUOTES_EDIT + SLASH + "{uid}")
    public ModelAndView getEdit(@PathVariable(name = "uid") String uid) {
        return new ModelAndView(VIEW_ADMIN_QUOTES_EDIT, MODEL_ITEM, service.findModelByUid(uid)
                .orElseThrow(() -> new ItemNotFoundException(uid)));
    }

    @PostMapping(PATH_QUOTES_EDIT + PATH_ROOT)
    public ModelAndView onCreate(QuoteModel model) {
        return new ModelAndView(VIEW_ADMIN_QUOTES_EDIT, MODEL_ITEM, service.saveNew(model));
    }

    @PostMapping(PATH_QUOTES_EDIT + SLASH + "{uid}")
    public ModelAndView onEdit(@PathVariable(name = "uid") String uid, QuoteModel model) {
        return new ModelAndView(VIEW_ADMIN_QUOTES_EDIT, MODEL_ITEM, service.saveByUid(uid, model));
    }
}
