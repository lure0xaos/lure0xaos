package gargoyle.l0x.services.app.convert;

import gargoyle.l0x.dto.app.NewsDto;
import gargoyle.l0x.entities.app.News;
import gargoyle.l0x.services.convert.EntityDtoConverter;
import gargoyle.l0x.services.users.convert.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NewsConverter implements EntityDtoConverter<LocalDateTime, News, NewsDto> {
    private final UserConverter userConverter;

    @Override
    public News toEntity(NewsDto dto) {
        return News.builder()
                .uid(dto.getUid())
                .head(dto.getHead())
                .source(dto.getSource())
                .author(userConverter.toEntity(dto.getAuthor()))
                .date(dto.getDate())
                .body(dto.getBody())
                .build();
    }

    @Override
    public NewsDto toDto(News entity) {
        return NewsDto.builder()
                .uid(entity.getUid())
                .head(entity.getHead())
                .source(entity.getSource())
                .author(userConverter.toDto(entity.getAuthor()))
                .date(entity.getDate())
                .body(entity.getBody())
                .build();
    }
}
