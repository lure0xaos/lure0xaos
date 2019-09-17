package gargoyle.l0x.services.app.convert;

import gargoyle.l0x.model.app.QuoteModel;
import gargoyle.l0x.entities.app.Quote;
import gargoyle.l0x.services.convert.EntityModelConverter;
import org.springframework.stereotype.Service;

@Service
public class QuoteConverter implements EntityModelConverter<String, Quote, QuoteModel> {
    @Override
    public Quote toEntity(QuoteModel model) {
        return Quote.builder()
                .uid(model.getUid())
                .head(model.getHead())
                .primarySource(model.getPrimarySource())
                .secondarySource(model.getSecondarySource())
                .author(model.getAuthor())
                .primaryBody(model.getPrimaryBody())
                .secondaryBody(model.getSecondaryBody())
                .build();
    }

    @Override
    public QuoteModel toModel(Quote entity) {
        return QuoteModel.builder()
                .uid(entity.getUid())
                .head(entity.getHead())
                .primarySource(entity.getPrimarySource())
                .secondarySource(entity.getSecondarySource())
                .author(entity.getAuthor())
                .primaryBody(entity.getPrimaryBody())
                .secondaryBody(entity.getSecondaryBody())
                .build();
    }
}
