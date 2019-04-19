package gargoyle.l0x.services.app.admin.news;

import gargoyle.l0x.dto.app.NewsDto;
import gargoyle.l0x.entities.app.News;
import gargoyle.l0x.repositories.app.NewsRepository;
import gargoyle.l0x.services.app.admin.base.BaseAdminService;
import gargoyle.l0x.services.app.convert.NewsConverter;
import gargoyle.l0x.services.auth.Auth;
import gargoyle.l0x.services.md.MD;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static gargoyle.l0x.services.app.admin.base.BaseAdminServiceUtil.*;

@Service
public class NewsAdminService extends BaseAdminService<LocalDateTime, NewsDto, News, NewsRepository> {
    private final Auth auth;
    private final MD md;

    public NewsAdminService(NewsRepository repository, Auth auth, MD md,
                            NewsConverter converter, NewsEntityService entityService) {
        super(NewsDto.class, converter, entityService);
        this.auth = auth;
        this.md = md;
    }

    @Override
    public NewsDto createDto() {
        return NewsDto.builder()
                .author(auth.getLoggedUser().orElseThrow())
                .build();
    }

    @Override
    public void updateEntity(News destination, NewsDto source) {
        updateSourceEntity(destination, source, md::toHtml);
        updateOwnerEntity(destination, source, () -> auth.getRealUser().orElseThrow());
        updateDatedEntity(destination, source);
    }
}
