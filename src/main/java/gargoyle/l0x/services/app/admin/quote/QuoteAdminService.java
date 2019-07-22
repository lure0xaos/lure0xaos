package gargoyle.l0x.services.app.admin.quote;

import gargoyle.l0x.dto.app.QuoteDto;
import gargoyle.l0x.entities.app.Quote;
import gargoyle.l0x.repositories.app.QuoteRepository;
import gargoyle.l0x.services.app.admin.base.BaseAdminService;
import gargoyle.l0x.services.app.convert.QuoteConverter;
import gargoyle.l0x.services.away.Away;
import gargoyle.l0x.services.md.MD;
import org.springframework.stereotype.Service;

import static gargoyle.l0x.services.app.admin.base.BaseAdminServiceUtil.updateBiSourceEntity;

@Service
public class QuoteAdminService extends BaseAdminService<String, QuoteDto, Quote, QuoteRepository> {
    private final MD md;
    private final Away away;

    public QuoteAdminService(QuoteRepository repository, Away away, MD md,
                             QuoteConverter converter, QuoteEntityService entityService) {
        super(QuoteDto.class, converter, entityService);
        this.away = away;
        this.md = md;
    }

    @Override
    public QuoteDto createDto() {
        return QuoteDto.builder()
                .build();
    }

    @Override
    public void updateEntity(Quote destination, QuoteDto source) {
        updateBiSourceEntity(destination, source, text -> away.rewriteText(md.toHtml(text)));
        destination.setAuthor(source.getAuthor());
    }
}
