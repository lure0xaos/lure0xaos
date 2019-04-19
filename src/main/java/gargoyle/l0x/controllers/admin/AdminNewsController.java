package gargoyle.l0x.controllers.admin;

import gargoyle.l0x.config.WebSecurityConfig;
import gargoyle.l0x.dto.app.NewsDto;
import gargoyle.l0x.services.app.admin.news.NewsAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;
import static gargoyle.l0x.controllers.admin.AdminController.MODEL_ITEM;
import static gargoyle.l0x.controllers.admin.AdminController.MODEL_ITEMS;

@Controller
@RequestMapping(WebSecurityConfig.PATH_ADMIN + AdminNewsController.PATH_NEWS)
@RequiredArgsConstructor
public class AdminNewsController {
    public static final String PATH_NEWS = "/news";
    public static final String PATH_NEWS_EDIT = "/edit";
    public static final String VIEW_ADMIN_NEWS = "admin/news/index";
    public static final String VIEW_ADMIN_NEWS_EDIT = "admin/news/edit";
    private final NewsAdminService service;

    @GetMapping(PATH_ROOT)
    public ModelAndView getIndex() {
        return new ModelAndView(VIEW_ADMIN_NEWS, MODEL_ITEMS, service.findAll(service.getDefaultSort()));
    }

    @GetMapping(PATH_NEWS_EDIT + "/")
    public ModelAndView getCreate() {
        return new ModelAndView(VIEW_ADMIN_NEWS_EDIT, MODEL_ITEM, service.createDto());
    }

    @GetMapping(PATH_NEWS_EDIT + "/{uid}")
    public ModelAndView getEdit(@PathVariable(name = "uid") String uid) {
        return new ModelAndView(VIEW_ADMIN_NEWS_EDIT, MODEL_ITEM, service.findDtoByUid(uid).orElseThrow());
    }

    @PostMapping(PATH_NEWS_EDIT + "/")
    public ModelAndView onCreate(NewsDto dto) {
        return new ModelAndView(VIEW_ADMIN_NEWS_EDIT, MODEL_ITEM, service.saveNew(dto));
    }

    @PostMapping(PATH_NEWS_EDIT + "/{uid}")
    public ModelAndView onEdit(@PathVariable(name = "uid") String uid, NewsDto dto) {
        return new ModelAndView(VIEW_ADMIN_NEWS_EDIT, MODEL_ITEM, service.saveByUid(uid, dto));
    }
}
