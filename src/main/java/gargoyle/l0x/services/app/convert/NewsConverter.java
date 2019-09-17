package gargoyle.l0x.services.app.convert;

import gargoyle.l0x.model.app.NewsModel;
import gargoyle.l0x.entities.app.News;
import gargoyle.l0x.services.convert.EntityModelConverter;
import gargoyle.l0x.services.users.convert.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NewsConverter implements EntityModelConverter<LocalDateTime, News, NewsModel> {
    private final UserConverter userConverter;

    @Override
    public News toEntity(NewsModel model) {
        return News.builder()
                .uid(model.getUid())
                .head(model.getHead())
                .source(model.getSource())
                .author(userConverter.toEntity(model.getAuthor()))
                .date(model.getDate())
                .body(model.getBody())
                .build();
    }

    @Override
    public NewsModel toModel(News entity) {
        return NewsModel.builder()
                .uid(entity.getUid())
                .head(entity.getHead())
                .source(entity.getSource())
                .author(userConverter.toModel(entity.getAuthor()))
                .date(entity.getDate())
                .body(entity.getBody())
                .build();
    }
}
