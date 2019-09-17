package gargoyle.l0x.services.app.convert;

import gargoyle.l0x.model.app.CreationModel;
import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.services.convert.EntityModelConverter;
import gargoyle.l0x.services.users.convert.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreationConverter implements EntityModelConverter<String, Creation, CreationModel> {
    private final UserConverter userConverter;

    @Override
    public Creation toEntity(CreationModel model) {
        return Creation.builder()
                .uid(model.getUid())
                .head(model.getHead())
                .source(model.getSource())
                .author(userConverter.toEntity(model.getAuthor()))
                .genre(model.getGenre())
                .date(model.getDate())
                .alias(model.getAlias())
                .body(model.getBody())
                .build();
    }

    @Override
    public CreationModel toModel(Creation entity) {
        return CreationModel.builder()
                .uid(entity.getUid())
                .head(entity.getHead())
                .source(entity.getSource())
                .author(userConverter.toModel(entity.getAuthor()))
                .genre(entity.getGenre())
                .date(entity.getDate())
                .alias(entity.getAlias())
                .body(entity.getBody())
                .build();
    }
}
