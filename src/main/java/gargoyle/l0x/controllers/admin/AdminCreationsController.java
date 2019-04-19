package gargoyle.l0x.controllers.admin;

import gargoyle.l0x.config.WebSecurityConfig;
import gargoyle.l0x.dto.app.CreationDto;
import gargoyle.l0x.services.app.admin.creation.CreationAdminService;
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
@RequestMapping(WebSecurityConfig.PATH_ADMIN + AdminCreationsController.PATH_CREATIONS)
@RequiredArgsConstructor
public class AdminCreationsController {
    public static final String PATH_CREATIONS = "/creations";
    public static final String PATH_CREATIONS_EDIT = "/edit";
    public static final String VIEW_ADMIN_CREATIONS = "admin/creations/index";
    public static final String VIEW_ADMIN_CREATIONS_EDIT = "admin/creations/edit";
    private final CreationAdminService service;

    @GetMapping(PATH_ROOT)
    public ModelAndView getIndex() {
        return new ModelAndView(VIEW_ADMIN_CREATIONS, MODEL_ITEMS, service.findAll(service.getDefaultSort()));
    }

    @GetMapping(PATH_CREATIONS_EDIT + "/")
    public ModelAndView getCreate() {
        return new ModelAndView(VIEW_ADMIN_CREATIONS_EDIT, MODEL_ITEM, service.createDto());
    }

    @GetMapping(PATH_CREATIONS_EDIT + "/{uid}")
    public ModelAndView getEdit(@PathVariable(name = "uid") String uid) {
        return new ModelAndView(VIEW_ADMIN_CREATIONS_EDIT, MODEL_ITEM, service.findDtoByUid(uid).orElseThrow());
    }

    @PostMapping(PATH_CREATIONS_EDIT + "/")
    public ModelAndView onCreate(CreationDto dto) {
        return new ModelAndView(VIEW_ADMIN_CREATIONS_EDIT, MODEL_ITEM, service.saveNew(dto));
    }

    @PostMapping(PATH_CREATIONS_EDIT + "/{uid}")
    public ModelAndView onEdit(@PathVariable(name = "uid") String uid, CreationDto dto) {
        return new ModelAndView(VIEW_ADMIN_CREATIONS_EDIT, MODEL_ITEM, service.saveByUid(uid, dto));
    }
}
