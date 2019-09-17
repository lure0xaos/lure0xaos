package gargoyle.l0x.services.app.admin.news;

import gargoyle.l0x.model.app.NewsModel;
import gargoyle.l0x.entities.app.News;
import gargoyle.l0x.err.NotLoggedInException;
import gargoyle.l0x.repositories.app.NewsRepository;
import gargoyle.l0x.services.app.admin.base.BaseAdminService;
import gargoyle.l0x.services.app.convert.NewsConverter;
import gargoyle.l0x.services.auth.Auth;
import gargoyle.l0x.services.away.Away;
import gargoyle.l0x.services.md.MD;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static gargoyle.l0x.services.app.admin.base.BaseAdminServiceUtil.*;

@Service
public class NewsAdminService extends BaseAdminService<LocalDateTime, NewsModel, News, NewsRepository> {
    private final Auth auth;
    private final Away away;
    private final MD md;

    public NewsAdminService(Auth auth, Away away, MD md,
                            NewsConverter converter, NewsEntityService entityService) {
        super(NewsModel.class, converter, entityService);
        this.auth = auth;
        this.away = away;
        this.md = md;
    }

    @Override
    public NewsModel createModel() {
        return NewsModel.builder()
                .author(auth.getLoggedUser().orElseThrow(NotLoggedInException::new))
                .build();
    }

    @Override
    public void updateEntity(News destination, NewsModel source) {
        updateSourceEntity(destination, source, text -> away.rewriteText(md.toHtml(text)));
        updateOwnerEntity(destination, source, () -> auth.getRealUser().orElseThrow(NotLoggedInException::new));
        updateDatedEntity(destination, source);
    }
}
