package gargoyle.l0x.controllers.admin;

import gargoyle.l0x.services.media.MediaService;
import gargoyle.l0x.util.IOUtil;
import gargoyle.l0x.util.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;

@Controller
@RequestMapping(AdminUploadController.PATH_BASE)
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class AdminUploadController {
    public static final String PATH_BASE = PATH_ROOT + "mdupload";
    public static final String PATH_DOWNLOAD = PATH_ROOT + "download";
    public static final String PATH_UPLOAD = PATH_ROOT + "upload";
    public static final Integer NO = Integer.valueOf(0);
    private static final Object[] NO_ARGS = new Object[0];
    private static final String ERROR_NO_FILE = "error-no-file";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_URL = "url";
    private static final String PARAM_UPLOAD = "editormd-image-file";
    public static final Integer YES = Integer.valueOf(1);
    private final MessageSource messages;
    private final MediaService service;

    @PostConstruct
    public void init() throws IOException {
        service.createDirectories();
    }

    @GetMapping(PATH_DOWNLOAD + PATH_ROOT + "{file}")
    public ResponseEntity<? extends Resource> mdFileDownload(@PathVariable("file") String file) {
        try {
            Resource resource = service.getFile(file);
            if (resource.exists())
                return IOUtil.getResponse(resource);
        } catch (IOException e) {
            log.error(noFile(), e);
        }
        return downloadFallback();
    }

    private ResponseEntity<? extends Resource> downloadFallback() {
        try {
            return IOUtil.getResponse(service.getFallback());
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getLocalizedMessage(), ex);
        }
    }

    private String noFile() {
        return messages.getMessage(ERROR_NO_FILE, NO_ARGS, LocaleContextHolder.getLocale());
    }

    @PostMapping(value = PATH_UPLOAD, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String mdFileUpload(@RequestParam(PARAM_UPLOAD) MultipartFile file) {
        if (file.isEmpty()) {
            return IOUtil.toJson(Maps.of(KEY_SUCCESS, NO, KEY_MESSAGE, noFile()), "{}");
        }
        try {
            service.save(file.getOriginalFilename(), file.getBytes());
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(PATH_BASE + PATH_DOWNLOAD).pathSegment(file.getOriginalFilename()).build().toUriString();
            return IOUtil.toJson(Maps.of(KEY_SUCCESS, YES, KEY_URL, uri), "{}");
        } catch (IOException e) {
            String message = e.getLocalizedMessage();
            log.error(message, e);
            return IOUtil.toJson(Maps.of(KEY_SUCCESS, NO, KEY_MESSAGE, message), "{}");
        }
    }

}
