package gargoyle.l0x.controllers.admin;

import gargoyle.l0x.config.WebSecurityConfig;
import gargoyle.l0x.err.ItemNotFoundException;
import gargoyle.l0x.model.app.CreationModel;
import gargoyle.l0x.services.app.admin.creation.CreationAdminService;
import gargoyle.l0x.util.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static gargoyle.l0x.config.AppConfig.SLASH;
import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;
import static gargoyle.l0x.controllers.admin.AdminController.MODEL_ITEM;
import static gargoyle.l0x.controllers.admin.AdminController.MODEL_ITEMS;

@Controller
@RequestMapping(WebSecurityConfig.PATH_ADMIN + AdminCreationsController.PATH_CREATIONS)
@RequiredArgsConstructor
public class AdminCreationsController {
    public static final String PATH_CREATIONS = PATH_ROOT + "creations";
    public static final String PATH_CREATIONS_EDIT = PATH_ROOT + "edit";
    public static final String VIEW_ADMIN_CREATIONS = "admin" + SLASH + "creations" + SLASH + "index";
    public static final String VIEW_ADMIN_CREATIONS_EDIT = "admin" + SLASH + "creations" + SLASH + "edit";
    public static final String MODEL_GENRES = "genres";
    private final CreationAdminService service;

    @GetMapping(PATH_ROOT)
    public ModelAndView getIndex() {
        return new ModelAndView(VIEW_ADMIN_CREATIONS, MODEL_ITEMS, service.findAll(service.getDefaultSort()));
    }

    @GetMapping(PATH_CREATIONS_EDIT + PATH_ROOT)
    public ModelAndView getCreate() {
        return new ModelAndView(VIEW_ADMIN_CREATIONS_EDIT, MODEL_ITEM, service.createModel());
    }

    @GetMapping(PATH_CREATIONS_EDIT + SLASH + "{uid}")
    public ModelAndView getEdit(@PathVariable(name = "uid") String uid) {
        return new ModelAndView(VIEW_ADMIN_CREATIONS_EDIT, Maps.of(
                MODEL_ITEM, service.findModelByUid(uid).orElseThrow(() -> new ItemNotFoundException(uid)),
                MODEL_GENRES, service.getGenres()
        ));
    }

    @PostMapping(PATH_CREATIONS_EDIT + PATH_ROOT)
    public ModelAndView onCreate(CreationModel model) {
        return new ModelAndView(VIEW_ADMIN_CREATIONS_EDIT, MODEL_ITEM, service.saveNew(model));
    }

    @PostMapping(PATH_CREATIONS_EDIT + SLASH + "{uid}")
    public ModelAndView onEdit(@PathVariable(name = "uid") String uid, CreationModel model,
                               @RequestParam(name = "genre-pre", required = false) String genrePre) {
        if (null != genrePre) model.setGenre(genrePre);
        return new ModelAndView(VIEW_ADMIN_CREATIONS_EDIT, Maps.of(
                MODEL_ITEM, service.saveByUid(uid, model),
                MODEL_GENRES, service.getGenres()
        ));
    }
}
