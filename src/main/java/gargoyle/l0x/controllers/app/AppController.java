package gargoyle.l0x.controllers.app;

import gargoyle.l0x.config.WebMvcConfig;
import gargoyle.l0x.services.app.App;
import gargoyle.l0x.services.app.convert.CommentConverter;
import gargoyle.l0x.services.app.convert.CreationConverter;
import gargoyle.l0x.services.app.convert.NewsConverter;
import gargoyle.l0x.services.app.convert.QuoteConverter;
import gargoyle.l0x.services.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static gargoyle.l0x.controllers.admin.AdminController.MODEL_ITEMS;

@Controller
@RequestMapping(WebMvcConfig.PATH_ROOT)
@RequiredArgsConstructor
public class AppController {
    public static final String VIEW_INDEX = "app/index";
    public static final String VIEW_CREATION = "app/creation";
    public static final String VIEW_CREATIONS = "app/creations";
    public static final String VIEW_CREATION_COMMENTS = "app/creation/comments";
    public static final String MODEL_COMMENTS = "comments";
    public static final String MODEL_CREATION = "creation";
    public static final String MODEL_NEWS = "news";
    public static final String MODEL_QUOTE = "quote";
    public static final String MODEL_VOTE = "vote";
    public static final String MODEL_RATE = "rate";
    public static final String MODEL_AUTH = "auth";
    private final App app;
    private final Auth auth;
    private final NewsConverter newsConverter;
    private final QuoteConverter quoteConverter;
    private final CreationConverter creationConverter;
    private final CommentConverter commentConverter;

    private static void checkin(HttpSession httpSession) {
        httpSession.setAttribute("checkin", LocalDateTime.now());
    }

    @RequestMapping("/find/")
    public ModelAndView findCreations(@RequestParam("query") String query) {
        return new ModelAndView(VIEW_CREATIONS, Map.of(
                MODEL_ITEMS, creationConverter.toDto(app.findCreationsByHeadOrSource(query.trim()))
        ));
    }

    @RequestMapping("/{alias:[-_a-z0-9]+}/vote/")
    public String voteCreation(@PathVariable("alias") String alias, @RequestParam("vote") int vote) {
        auth.getLoggedUser().ifPresent(userDto -> app.vote(alias, userDto.getUsername(), vote));
        return "redirect:/" + alias;
    }

    @RequestMapping(value = "/{alias:[-_a-z0-9]+}/comment/"/*, consumes = MediaType.APPLICATION_JSON_VALUE*/)
    @ResponseBody
    public String commentCreation(@PathVariable("alias") String alias, @RequestParam("head") String head, @RequestParam("source") String source) {
        auth.getLoggedUser().ifPresent(userDto -> app.comment(alias, userDto.getUsername(), head, source));
        return "ok";
    }

    @RequestMapping(value = "/{alias:[-_a-z0-9]+}/comments/"/*, consumes = MediaType.APPLICATION_JSON_VALUE*/)
    @ResponseBody
    public ModelAndView commentCreationComments(@PathVariable("alias") String alias) {
        return app.findCreation(alias)
                .map(creation -> new ModelAndView(VIEW_CREATION_COMMENTS, Map.of(
                        MODEL_COMMENTS, commentConverter.toDto(app.getComments(creation)),
                        MODEL_AUTH, auth.isFullyLogged()
                )))
                .orElseGet(() -> (new ModelAndView(VIEW_CREATION_COMMENTS, Map.of(
                        MODEL_COMMENTS, List.of(),
                        MODEL_AUTH, auth.isFullyLogged()
                ))));
    }

    @GetMapping(WebMvcConfig.PATH_ROOT)
    public ModelAndView getIndex(HttpSession httpSession) {
        checkin(httpSession);
        return new ModelAndView(VIEW_INDEX, Map.of(
                MODEL_NEWS, newsConverter.toDto(app.getFirstNews(5)),
                MODEL_QUOTE, quoteConverter.toDto(app.getRandomQuote())
        ));
    }

    @GetMapping("/{alias:[-_a-z0-9]+}")
    public ModelAndView getCreation(@PathVariable("alias") String alias, HttpSession httpSession) {
        checkin(httpSession);
        return app.findCreation(alias)
                .map(creation -> new ModelAndView(VIEW_CREATION, Map.of(
                        MODEL_CREATION, creationConverter.toDto(creation),
                        MODEL_COMMENTS, commentConverter.toDto(app.getComments(creation)),
                        MODEL_VOTE, app.getVote(alias),
                        MODEL_RATE, app.getVote(alias),
                        MODEL_AUTH, auth.isFullyLogged()
                )))
                .orElseGet(() -> getIndex(httpSession));
    }
}
