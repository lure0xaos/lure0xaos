package gargoyle.l0x.controllers.app;

import gargoyle.l0x.services.app.App;
import gargoyle.l0x.services.app.convert.CommentConverter;
import gargoyle.l0x.services.app.convert.CreationConverter;
import gargoyle.l0x.services.app.convert.NewsConverter;
import gargoyle.l0x.services.app.convert.QuoteConverter;
import gargoyle.l0x.services.auth.Auth;
import gargoyle.l0x.util.Lists;
import gargoyle.l0x.util.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static gargoyle.l0x.config.AppConfig.SLASH;
import static gargoyle.l0x.config.WebMvcConfig.PATH_ROOT;
import static gargoyle.l0x.controllers.admin.AdminController.MODEL_ITEMS;

@Controller
@RequestMapping(PATH_ROOT)
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

    @RequestMapping(PATH_ROOT + "find" + PATH_ROOT)
    public ModelAndView findCreations(@RequestParam("query") String query) {
        return new ModelAndView(VIEW_CREATIONS, Maps.of(
                MODEL_ITEMS, creationConverter.toModel(app.findCreationsByHeadOrSource(query.trim()))
        ));
    }

    @RequestMapping(PATH_ROOT + "{alias:[-_a-z0-9]+}" + SLASH + "vote" + PATH_ROOT)
    public String voteCreation(@PathVariable("alias") String alias, @RequestParam("vote") int vote) {
        auth.getLoggedUser().ifPresent(userModel -> app.vote(alias, userModel.getUsername(), vote));
        return "redirect:/" + alias;
    }

    @RequestMapping(PATH_ROOT + "{alias:[-_a-z0-9]+}" + SLASH + "comment" + PATH_ROOT)
    @ResponseBody
    public String commentCreation(@PathVariable("alias") String alias, @RequestParam("head") String head,
                                  @RequestParam("source") String source) {
        auth.getLoggedUser().ifPresent(userModel -> app.comment(alias, userModel.getUsername(), head, source));
        return "ok";
    }

    @RequestMapping(PATH_ROOT + "{alias:[-_a-z0-9]+}" + SLASH + "comments" + PATH_ROOT)
    @ResponseBody
    public ModelAndView commentCreationComments(@PathVariable("alias") String alias) {
        return app.findCreation(alias)
                .map(creation -> new ModelAndView(VIEW_CREATION_COMMENTS, Maps.of(
                        MODEL_COMMENTS, commentConverter.toModel(app.getComments(creation)),
                        MODEL_AUTH, auth.isFullyLogged()
                )))
                .orElseGet(() -> (new ModelAndView(VIEW_CREATION_COMMENTS, Maps.of(
                        MODEL_COMMENTS, Lists.of(),
                        MODEL_AUTH, auth.isFullyLogged()
                ))));
    }

    @GetMapping(PATH_ROOT)
    public ModelAndView getIndex(HttpSession httpSession) {
        checkin(httpSession);
        return new ModelAndView(VIEW_INDEX, Maps.of(
                MODEL_NEWS, newsConverter.toModel(app.getFirstNews(5)),
                MODEL_QUOTE, quoteConverter.toModel(app.getRandomQuote())
        ));
    }

    @GetMapping(PATH_ROOT + "{alias:[-_a-z0-9]+}")
    public ModelAndView getCreation(@PathVariable("alias") String alias, HttpSession httpSession) {
        checkin(httpSession);
        return app.findCreation(alias)
                .map(creation -> {
                    int vote = app.getVote(alias);
                    return new ModelAndView(VIEW_CREATION, Maps.of(
                            MODEL_CREATION, creationConverter.toModel(creation),
                            MODEL_COMMENTS, commentConverter.toModel(app.getComments(creation)),
                            MODEL_VOTE, vote,
                            MODEL_RATE, vote,
                            MODEL_AUTH, auth.isFullyLogged()
                    ));
                })
                .orElseGet(() -> getIndex(httpSession));
    }
}
