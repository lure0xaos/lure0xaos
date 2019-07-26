package gargoyle.l0x.controllers.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gargoyle.l0x.config.MediaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Controller
@RequestMapping(AdminUploadController.PATH_BASE)
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class AdminUploadController {
    public static final String PATH_BASE = "/mdupload";
    public static final String PATH_DOWNLOAD = "/download";
    public static final String PATH_UPLOAD = "/upload";
    private static final Object[] NO_ARGS = new Object[0];
    private static final String ERROR_NO_FILE = "error-no-file";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_URL = "url";
    private static final String RES_FALLBACK = "static/img/No_Image-250x250.png";
    private static final String PARAM_UPLOAD = "editormd-image-file";
    private final MessageSource messages;
    private final MediaConfig config;

    @GetMapping(PATH_DOWNLOAD + "/{file}")
    public ResponseEntity<? extends Resource> mdFileDownload(@PathVariable("file") String file) {
        try {
            Resource resource = new FileSystemResource(Paths.get(config.getImageLocation(), file));
            return ResponseEntity.ok()
                    .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (IOException e) {
            log.error(messages.getMessage(ERROR_NO_FILE, NO_ARGS, LocaleContextHolder.getLocale()), e);
            try {
                Resource resource = new ClassPathResource(RES_FALLBACK);
                return ResponseEntity.ok()
                        .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .contentLength(resource.contentLength())
                        .body(resource);
            } catch (IOException ex) {
                throw new IllegalArgumentException(ex.getLocalizedMessage(), ex);
            }
        }
    }

    @PostMapping(value = PATH_UPLOAD, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String mdFileUpload(@RequestParam(PARAM_UPLOAD) MultipartFile file, UriComponentsBuilder ucb) {
        if (file.isEmpty()) {
            try {
                return new ObjectMapper().writeValueAsString(Map.of(KEY_SUCCESS, 0,
                        KEY_MESSAGE, messages.getMessage(ERROR_NO_FILE, NO_ARGS, LocaleContextHolder.getLocale())));
            } catch (JsonProcessingException e) {
                return "{}";
            }
        }
        try {
            Files.createDirectories(Paths.get(config.getImageLocation()));
            Files.write(Paths.get(config.getImageLocation(), file.getOriginalFilename()), file.getBytes());
            try {
                return new ObjectMapper().writeValueAsString(Map.of(KEY_SUCCESS, 1,
                        KEY_URL, ucb.path(PATH_BASE + PATH_DOWNLOAD)
                                .pathSegment(file.getOriginalFilename())
                                .build().toUriString()));
            } catch (JsonProcessingException e) {
                return "{}";
            }
        } catch (IOException e) {
            String message = e.getLocalizedMessage();
            log.error(message, e);
            try {
                return new ObjectMapper().writeValueAsString(Map.of(KEY_SUCCESS, 0, KEY_MESSAGE, message));
            } catch (JsonProcessingException jpe) {
                return "{}";
            }
        }
    }

}