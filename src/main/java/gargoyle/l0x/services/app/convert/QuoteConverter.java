package gargoyle.l0x.services.app.convert;

import gargoyle.l0x.dto.app.QuoteDto;
import gargoyle.l0x.entities.app.Quote;
import gargoyle.l0x.services.convert.EntityDtoConverter;
import org.springframework.stereotype.Service;

@Service
public class QuoteConverter implements EntityDtoConverter<String, Quote, QuoteDto> {
    @Override
    public Quote toEntity(QuoteDto dto) {
        return Quote.builder()
                .uid(dto.getUid())
                .head(dto.getHead())
                .primarySource(dto.getPrimarySource())
                .secondarySource(dto.getSecondarySource())
                .author(dto.getAuthor())
                .primaryBody(dto.getPrimaryBody())
                .secondaryBody(dto.getSecondaryBody())
                .build();
    }

    @Override
    public QuoteDto toDto(Quote entity) {
        return QuoteDto.builder()
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
