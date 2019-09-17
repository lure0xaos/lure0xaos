package gargoyle.l0x.services.app.admin.quote;

import gargoyle.l0x.entities.app.Quote;
import gargoyle.l0x.repositories.app.QuoteRepository;
import gargoyle.l0x.services.app.admin.base.BaseEntityService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuoteEntityService extends BaseEntityService<String, Quote, QuoteRepository> {
    public QuoteEntityService(QuoteRepository repository) {
        super(Quote.class, repository);
    }

    @Override
    protected Quote createEntity() {
        return new Quote();
    }

    @Override
    public Sort getDefaultSort() {
        return Sort.by(Sort.Direction.ASC, "id");
    }
}
