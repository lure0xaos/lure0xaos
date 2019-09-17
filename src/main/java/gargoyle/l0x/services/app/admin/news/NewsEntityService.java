package gargoyle.l0x.services.app.admin.news;

import gargoyle.l0x.entities.app.News;
import gargoyle.l0x.repositories.app.NewsRepository;
import gargoyle.l0x.services.app.admin.base.BaseEntityService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NewsEntityService extends BaseEntityService<LocalDateTime, News, NewsRepository> {
    public NewsEntityService(NewsRepository repository) {
        super(News.class, repository);
    }

    @Override
    protected News createEntity() {
        return new News();
    }

    @Override
    public Sort getDefaultSort() {
        return Sort.by(Sort.Direction.ASC, "date");
    }
}
