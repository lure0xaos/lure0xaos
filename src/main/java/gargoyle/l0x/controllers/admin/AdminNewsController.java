package gargoyle.l0x.controllers.admin;

import gargoyle.l0x.config.WebSecurityConfig;
import gargoyle.l0x.model.app.NewsModel;
import gargoyle.l0x.err.ItemNotFoundException;
import gargoyle.l0x.services.app.admin.news.NewsAdminService;
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
@RequestMapping(WebSecurityConfig.PATH_ADMIN + AdminNewsController.PATH_NEWS)
@RequiredArgsConstructor
public class AdminNewsController {
    public static final String PATH_NEWS = PATH_ROOT + "news";
    public static final String PATH_NEWS_EDIT = PATH_ROOT + "edit";
    public static final String VIEW_ADMIN_NEWS = "admin" + SLASH + "news" + SLASH + "index";
    public static final String VIEW_ADMIN_NEWS_EDIT = "admin" + SLASH + "news" + SLASH + "edit";
    private final NewsAdminService service;

    @GetMapping(PATH_ROOT)
    public ModelAndView getIndex() {
        return new ModelAndView(VIEW_ADMIN_NEWS, MODEL_ITEMS, service.findAll(service.getDefaultSort()));
    }

    @GetMapping(PATH_NEWS_EDIT + PATH_ROOT)
    public ModelAndView getCreate() {
        return new ModelAndView(VIEW_ADMIN_NEWS_EDIT, MODEL_ITEM, service.createModel());
    }

    @GetMapping(PATH_NEWS_EDIT + SLASH + "{uid}")
    public ModelAndView getEdit(@PathVariable(name = "uid") String uid) {
        return new ModelAndView(VIEW_ADMIN_NEWS_EDIT, MODEL_ITEM, service.findModelByUid(uid)
                .orElseThrow(() -> new ItemNotFoundException(uid)));
    }

    @PostMapping(PATH_NEWS_EDIT + PATH_ROOT)
    public ModelAndView onCreate(NewsModel model) {
        return new ModelAndView(VIEW_ADMIN_NEWS_EDIT, MODEL_ITEM, service.saveNew(model));
    }

    @PostMapping(PATH_NEWS_EDIT + SLASH + "{uid}")
    public ModelAndView onEdit(@PathVariable(name = "uid") String uid, NewsModel model) {
        return new ModelAndView(VIEW_ADMIN_NEWS_EDIT, MODEL_ITEM, service.saveByUid(uid, model));
    }
}
